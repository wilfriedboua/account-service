package org.sid.accountserviceaxonnew.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.sid.accountserviceaxonnew.commonapi.commands.CreateAccountCommand;
import org.sid.accountserviceaxonnew.commonapi.commands.CreditAccountCommand;
import org.sid.accountserviceaxonnew.commonapi.commands.DebitAccountCommand;
import org.sid.accountserviceaxonnew.commonapi.enums.AccountStatus;
import org.sid.accountserviceaxonnew.commonapi.events.AccountCreatedEvent;
import org.sid.accountserviceaxonnew.commonapi.events.AccountCreditedEvent;
import org.sid.accountserviceaxonnew.commonapi.events.AccountDebitedEvent;
import org.sid.accountserviceaxonnew.commonapi.exceptions.NegativeInitialBalanceException;

@Aggregate
public class AccountAggregate {
	
	@AggregateIdentifier
	private String accountId;
	
	private String currency;
	
	private double balance;
	private AccountStatus status;
	
	public AccountAggregate() {
		//required by AXON
	}
	
	@CommandHandler
	public AccountAggregate (CreateAccountCommand command) {
		//System.out.println("Id Command : "+command.getId());
		if(command.getInitialBalance()<0) throw new NegativeInitialBalanceException("Negative ");
		AggregateLifecycle.apply(new AccountCreatedEvent(
				command.getId(),
				command.getCurrency(),
				command.getInitialBalance(),
				AccountStatus.CREATED
				
				));
		
	}
	
	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		this.accountId=event.getId();
		this.currency=event.getCurrency();
		this.balance=event.getBalance();
		this.status=event.getStatus();
		
		
	}
	
	@CommandHandler
	public AccountAggregate (CreditAccountCommand command) {
		//System.out.println("Id Command : "+command.getId());
		if(command.getAmount()<0) throw new NegativeInitialBalanceException("Negative Amount");
		System.out.println("Id Command Credit: "+command.getId());
		AggregateLifecycle.apply(new AccountCreditedEvent(
				command.getId(),
				command.getCurrency(),
				command.getAmount()
				));
		System.out.println("Account IDcrediAcountAgregate:"+this.accountId);
		System.out.println("OK");
	}
	
	@CommandHandler
	public AccountAggregate (DebitAccountCommand command) {
		//System.out.println("Id Command : "+command.getId());
		if(command.getAmount()<0) throw new NegativeInitialBalanceException("Negative Amount");
		if(command.getAmount()>this.balance) throw new RuntimeException("Solde insufisant");
		AggregateLifecycle.apply(new AccountDebitedEvent(
				command.getId(),
				command.getCurrency(),
				command.getAmount()
				));
		
	}
	
	
	@EventSourcingHandler
	public void on(AccountCreditedEvent event) {
		this.accountId=event.getId();
		System.out.println("Balance avt credit :"+balance);
		System.out.println("ID event:"+event.getId());
		System.out.println("Account ID:"+this.accountId);
		this.balance+=event.getAmount();
		System.out.println("Balance après credit :"+balance);
	}
	
	@EventSourcingHandler
	public void on(AccountDebitedEvent event) {
		this.balance-=event.getAmount();
	}
}
