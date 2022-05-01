package com.kafka.client.chapter4;

import com.kafka.client.chapter4.model.Player;
import com.kafka.client.chapter4.model.Product;
import com.kafka.client.chapter4.model.ScoreEvent;
import com.kafka.client.chapter4.model.join.ScoreWithPlayer;
import com.kafka.client.chapter4.serialization.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

public class LeaderboardTopologyVersion {
    public static void main(String[] args) {
        // the builder is used to construct the topology
        StreamsBuilder builder = new StreamsBuilder();

        // register the score events stream
        KStream<String, ScoreEvent> scoreEvents =
                builder.stream(
                        "score-events",
                        Consumed.with(Serdes.ByteArray(), JsonSerdes.ScoreEvent())
                ).selectKey((k, v) -> v.getPlayerId().toString());

        // create the partitioned players table
        KTable<String, Player> players =
                builder.table(
                        "players",
                        Consumed.with(Serdes.String(), JsonSerdes.Player()));

        // create the global product table
        GlobalKTable<String, Product> products =
                builder.globalTable(
                        "products",
                        Consumed.with(Serdes.String(), JsonSerdes.Product())
                );

        // join params for scoreEvents - players join
        Joined<String, ScoreEvent, Player> playerJoinParams =
                Joined.with(Serdes.String(), JsonSerdes.ScoreEvent(), JsonSerdes.Player());

        // join scoreEvents - players
        ValueJoiner<ScoreEvent, Player, ScoreWithPlayer> scorePlayerJoiner =
                ScoreWithPlayer::new;
        KStream<String, ScoreWithPlayer> withPlayers =
                scoreEvents.join(players, scorePlayerJoiner, playerJoinParams);

        // map score-with-player records to products
        KeyValueMapper<String, ScoreWithPlayer, String> keyMapper =
                (leftKey, scoreWithPlayer) -> {
                    return String.valueOf(scoreWithPlayer.getScoreEvent().getProductId());
                };

    }
}
