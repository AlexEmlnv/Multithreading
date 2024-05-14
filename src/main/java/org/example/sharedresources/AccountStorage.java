package org.example.sharedresources;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountStorage {
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            Account previous = accounts.putIfAbsent(account.getId(), account);
            return previous == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            Account previous = accounts.replace(account.getId(), account);
            return previous != null;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            Optional<Account> fromAccount = this.getById(fromId);
            Optional<Account> toAccount = this.getById(toId);
            if (!fromAccount.isPresent() || !toAccount.isPresent()
                || fromAccount.get().getAmount() <  amount) {
                return false;
            }
            this.update(new Account(fromId, fromAccount.get().getAmount() - amount));
            this.update(new Account(toId, toAccount.get().getAmount() + amount));
            return true;
        }
    }
}
