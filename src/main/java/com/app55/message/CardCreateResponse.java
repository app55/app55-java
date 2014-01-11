package com.app55.message;

import org.codehaus.jackson.annotate.JsonProperty;

import com.app55.domain.Card;

public final class CardCreateResponse extends Response
{
	private Card	card;
	private String threeDSecureRedirectUrl;
	
	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}
	@JsonProperty(value = "threeds")
	public String getThreeDSecureRedirectUrl() {
		return threeDSecureRedirectUrl;
	}

	public void setThreeDSecureRedirectUrl(String threeDSecureRedirectUrl) {
		this.threeDSecureRedirectUrl = threeDSecureRedirectUrl;
	}

}