package com.theironyard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck) {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for (Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits = hand.stream()
                .map(card -> card.suit)
                .collect(Collectors.toCollection(HashSet<Card.Suit>::new));
        return suits.size() ==1;
    }

    static boolean isFourOfAKind(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(HashSet<Card.Rank>::new));
        return ranks.size() ==1;
    }

    static boolean isStraightFlush(HashSet<Card> hand) {
        if (isFlush(hand) && straight(hand)) {
            return true;

        }
        else return false;

    }

    static boolean isThreeOfAKind(HashSet<Card> hand) {
        ArrayList<Card.Rank> ranks = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(ArrayList<Card.Rank>::new));
        for (Card.Rank rank : Card.Rank.values()) {
            int freq = Collections.frequency(ranks, rank);

            if (freq == 3) {
                return true;
            }

        }
        return false;
    }

    static boolean isTwoPair(HashSet<Card> hand) {
        ArrayList<Card.Rank> ranks = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(ArrayList<Card.Rank>::new));
        int pairs = 0;
        for (Card.Rank rank : Card.Rank.values()) {
            int freq = Collections.frequency(ranks, rank);

            if (freq == 2) {
                pairs++;
            }

        }
        if (pairs == 2) {
            return true;
        }
        else {
            return false;
        }
    }



    static boolean straight(HashSet<Card> hand) {
        ArrayList<Integer> sortedArray = hand.stream()
                .map(card -> card.rank.ordinal())
                .sorted((x1, x2) -> Integer.compare(x1.intValue(), x2.intValue()))
                .collect(Collectors.toCollection(ArrayList<Integer>::new));

        ArrayList<Integer> secondArray = new ArrayList<>();
        int rank = sortedArray.get(0) + 1;
        while (secondArray.size() < sortedArray.size()) {
            secondArray.add(rank);
            rank++;
        }

        return sortedArray.equals(secondArray);
    }


    public static void main(String[] args) {
        HashSet<Card> deck = createDeck();
        HashSet<HashSet<Card>> hands = createHands(deck);
        HashSet<HashSet<Card>> flushes = hands.stream()
                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        System.out.println(flushes.size());


    }

}

