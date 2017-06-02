#### Run GrabReceiptsForwarder by running `gradle -q run`
You do need to have your own client_secret.json file associated with your authenticated account

Follow the tutorial guide and official documentation for more:

[Tutorial guide](https://developers.google.com/gmail/api/quickstart/java)

New since last check-in:
* primary functionality achieved, development parked

Future improvement opportunity:
* for some reason, the email sent was recognized really slowly by expensify's smart scan compared to forwarding such emails manually, investigation may be required to find out root cause

TO-DOs:
* ~~parse message properly~~
* ~~send message with MIME body~~
* ~~set date to Monday - Friday of the passing week (or generalize in the future)~~
* ~~take input from somewhere to generalize the date range of receipts (but not what i need)~~
* complete tests
* ~~refactor code~~
* ~~use REST api not client api? -nope, client api works~~
* ~~test sending receipts to expensify~~
* ...
