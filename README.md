# Grundaufgabe :heavy_check_mark:

Unsere Datenbasis besteht aus Zwei Tabellen. Die erste beinhaltet
Accounts, Namen und E-Mail-Adresse. Beispiel:

| accountId | email             | name  |
| ---------:| ----------------- | ----- |
|        23 | alice@example.com | Alice |
|        42 | bob@example.com   | Bob   |
|      1337 | cindy@example.com | Cindy |

Die zweite Tabelle beinhaltet die Information, ob ein Account E-Mails an einen anderen Account senden darf. Im folgenden Beispiel darf Alice Mails an Bob und Bob Mails an Cindy senden.

| sender | receiver |
| ------:| --------:|
|     23 |       42 |
|   1337 |       23 |

Schreibe eine Spring-Applikation, die das Versenden von Mails über eine JSON-REST-API möglich macht.

Vorerst sollen zwei REST-Endpoints definiert werden:

- `GET /{sender-ID}/receivers`
	- Ein Aufruf soll eine Liste an Account-IDs zurückgeben, an die der Account mit der `sender-ID` Mails versenden darf
- `POST /{sender-ID}/send/{receiver-ID}`
	- Ein Aufruf soll eine E-Mail mit dem folgenden Text versenden:
		
		```Hallo ${Empfänger-Name}, ich bin ${Sender-Name}!```

Dabei sollen die Informationen aus einer SQL-Datenbank mittels JPA-Repositories geladen werden.

Der Mail-Versand kann dabei zuerst durch Aufrufe von `println` simuliert werden. Für den richtigen Mailversand musst du dir leider ein Wegwerf-E-Mail-Konto erstellen. Dieses soll dann _immer_ die Absender-Adresse sein.

## Zusatzaufgabe: Mailversand und Debugging :heavy_check_mark:

Den Mailversand hätten wir gern in Zwei-Varianten:

- Ein Service versendet richtige Mails
- Der zweite Service schreibt den Mail-Text mit `println` in die Standardausgabe

Welcher der beiden Services verwendet werden soll, soll in der `application.properties`-Datei einstellbar sein.

## Zusatzaufgabe: Speichern der Mails :heavy_check_mark:

Die versendeten Mails sollen in der Datenbank gespeichert werden. Gespeichert werden sollen mindestens:

- Sender-ID
- Empfänger-ID
- Zeitpunkt der Erstellung

Durch zwei neue REST-Endpoints sollen die versendeten Mails abgerufen werden können:

- `GET /{sender-ID}/sentMails`
- `GET /{receiver-ID}/receivedMails`

## Zusatzaufgabe: Spring-Security

Verwende Spring Security, um den Zugriff auf die REST-Endpoints mit einem Passwort abzusichern.

Bonus 1: Erweitere die Account-Tabelle um eine Passwort-Spalte und verwende die Account-Tabelle als Datenbasis für die Passwortabfrage.

Bonus 2: ermögliche *nur* dem Account mit der ID `{sender-ID}` dem Zugriff auf z.B. `/{sender-ID}/sentMails`.

## Zusatzaufgabe: Thymeleaf

Verwende Thymeleaf, um die Ersetzungen im Text zu machen. Gerne darfst du dann auch HTML-Mails erstellen.

Bonus: Internationalisiere die Nachricht (Deutsch und Englisch reicht ;) mit `.properties`-Dateien.
