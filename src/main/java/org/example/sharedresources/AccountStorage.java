package org.example.sharedresources;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountStorage {
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            if (accounts.containsKey(account.getId())) {
                return false;
            }
            accounts.put(account.getId(), account);
            return true;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            if (!accounts.containsKey(account.getId())) {
                return false;
            }
            accounts.put(account.getId(), account);
            return true;
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
            if (!accounts.containsKey(fromId) || !accounts.containsKey(toId) || amount <= 0
                || this.getById(fromId).get().getAmount() < amount || fromId == toId
                || (this.getById(toId).get().getAmount() + amount) > Integer.MAX_VALUE) {
                return false;
            }
            Account fromAccount = this.getById(fromId).get();
            Account toAccount = this.getById(toId).get();
            this.update(new Account(fromId, fromAccount.getAmount() - amount));
            this.update(new Account(toId, toAccount.getAmount() + amount));
            return true;
        }
    }
}
