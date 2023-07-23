--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `database`.`domain_event_entry` DROP INDEX `database`.`UK8s1f994p4la2ipb13me2xqm1w`;

CREATE UNIQUE INDEX `UK8s1f994p4la2ipb13me2xqm1w` ON `database`.`domain_event_entry` (`sequence_number` ASC);

