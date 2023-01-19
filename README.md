# Dirty Read Phenomena

The phenomena that is being demonstrated in this example is the phenomenon of dirty read. It occurs when a transaction reads data that is in an inconsistent state. In this example, the first transaction updates a row in the student table, but has not yet committed the changes. The second transaction then reads the same row, but the changes made by the first transaction are not yet visible to it. This results in the second transaction reading data that is in an inconsistent state.

Achieving this phenomena is done by running two different transactions in parallel and not committing the first transaction before the second transaction reads the data. This allows the second transaction to read the data before it has been committed and therefore see the dirty data.

## How it can be achieved?

In this example, we can see that the isolation level is set to the default level, which is READ COMMITTED. This means that if we run the above code, the result will be the original firstname, before the update in the first transaction. The second transaction reads only committed data, so the changes made in the first transaction are not visible yet. To see the changes from the first transaction, we need to commit it first.