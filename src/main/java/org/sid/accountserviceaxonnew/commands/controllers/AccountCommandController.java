package org.sid.accountserviceaxonnew.commands.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.sid.accountserviceaxonnew.commonapi.commands.CreateAccountCommand;
import org.sid.accountserviceaxonnew.commonapi.commands.CreditAccountCommand;
import org.sid.accountserviceaxonnew.commonapi.commands.DebitAccountCommand;
import org.sid.accountserviceaxonnew.commonapi.dtos.CreateAccountRequestDTO;
import org.sid.accountserviceaxonnew.commonapi.dtos.CreditAccountRequestDTO;
import org.sid.accountserviceaxonnew.commonapi.dtos.DebitAccountRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/account")
public class AccountCommandController {
	
	private CommandGateway commandGateway;
	private String id;
	
	private EventStore eventStore;
	
	public AccountCommandController (CommandGateway commandGateway, EventStore eventStore) {
		this.commandGateway=commandGateway;
		this.eventStore=eventStore;
	}
	
	@PostMapping ("/create")
	public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDTO request ) {
		id=UUID.randomUUID().toString();
		//System.out.println("UUID.randomUUID : "+id);
		return commandGateway.send(new CreateAccountCommand(
				 //UUID.randomUUID().toString(),
				 id, 
				 request.getCurrency(),
				 request.getInitialBalance()
				 
				 ));			 
				 
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exeptionHandler(Exception exception){
		return new ResponseEntity<> (exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@PostMapping ("/debit")
	public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request ) {
		id=UUID.randomUUID().toString();
		//System.out.println("UUID.randomUUID : "+id);
		return commandGateway.send(new DebitAccountCommand(
				 //UUID.randomUUID().toString(),
				 request.getAccountId(),
				 request.getCurrency(),
				 request.getAmount()
				 
				 ));			 
				 
	}
	
	@PostMapping ("/credit")
	public CompletableFuture<String> debitAccount(@RequestBody CreditAccountRequestDTO request ) {
		id=UUID.randomUUID().toString();
		//System.out.println("UUID.randomUUID : "+id);
		return commandGateway.send(new CreditAccountCommand(
				 //UUID.randomUUID().toString(),
				 request.getAccountId(),
				 request.getCurrency(),
				 request.getAmount()
				 
				 ));			 
				 
	}
	
	@GetMapping ("/eventStore/{id}")
	public Stream eventStore(@PathVariable String id) {
		return eventStore.readEvents(id).asStream();
	}
}
