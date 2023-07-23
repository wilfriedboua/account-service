--<ScriptOptions statementTerminator=";"/>

CREATE TABLE domain_event_entry (
	global_index BIGINT NOT NULL,
	event_identifier VARCHAR(255) NOT NULL,
	meta_data LONGBLOB,
	payload LONGBLOB NOT NULL,
	payload_revision VARCHAR(255),
	payload_type VARCHAR(255) NOT NULL,
	time_stamp VARCHAR(255) NOT NULL,
	aggregate_identifier VARCHAR(255) NOT NULL,
	sequence_number BIGINT NOT NULL,
	type VARCHAR(255),
	PRIMARY KEY (global_index)
);

CREATE UNIQUE INDEX UK_fwe6lsa8bfo6hyas6ud3m8c7x ON domain_event_entry (event_identifier ASC);

CREATE UNIQUE INDEX UK8s1f994p4la2ipb13me2xqm1w ON domain_event_entry (aggregate_identifier ASC);

CREATE UNIQUE INDEX UK8s1f994p4la2ipb13me2xqm1w ON domain_event_entry (sequence_number ASC);

