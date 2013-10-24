package com.app55.message;

import java.util.List;

import com.app55.domain.Card;

public final class CardListResponse extends Response
{
	private List<Card>	cards;

	public List<Card> getCards()
	{
		return cards;
	}

	public void setCards(List<Card> cards)
	{
		this.cards = cards;
	}
}