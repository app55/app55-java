package com.app55.message;

import com.app55.domain.Card;

public final class CardCreateResponse extends Response
{
	private Card	card;

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}
}