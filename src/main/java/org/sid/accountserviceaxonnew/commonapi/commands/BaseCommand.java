package org.sid.accountserviceaxonnew.commonapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

public class BaseCommand <T>{
	
	
	@TargetAggregateIdentifier
	@Getter private T id;

	public BaseCommand(T id) {
		super();
		this.id = id;
	}

	
}
