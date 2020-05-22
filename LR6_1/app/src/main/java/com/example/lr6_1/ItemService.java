package com.example.lr6_1;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

class ItemService {
    int maxId = 0;
    private List<Item> items;
    private List<DataChangedListener> listeners;
    private static final ItemService ourInstance = new ItemService();

    static ItemService getInstance() {
        return ourInstance;
    }

    public List<Item> getAvailableItems() {
        List<Item> availableItems = new LinkedList<>();
        for(Item item : items) {
            if(item.getCount() > 0)
                availableItems.add(item);
        }
        return availableItems;
    }

    public void addDataChangedListener(DataChangedListener listener) {
        listeners.add(listener);
    }

    public void addItem(Item newItem) {
        newItem.setId(maxId + 1);
        maxId +=1;
        items.add(newItem);
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }

    public void deleteItem(int id) {
        for(Item item : items) {
            if(item.getId() == id)
                items.remove(item);
        }
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }

    public void updateItem(Item updatedItem) {
        for(Item item :items) {
            if(item.getId() == updatedItem.getId()) {
                    items.set(items.indexOf(item), updatedItem);
                    Cart.getInstance().updateItem(updatedItem);
            }
        }
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }

    public void removeListener(DataChangedListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public List<Item> getItems() {
        return items;
    }

    public void performPurchase(Cart cart) {
        for(Item item : cart.getItemsArray()) {
            item.setCount(item.getCount() - cart.getCount(item));
        }
        listeners.forEach(new Consumer<DataChangedListener>() {
            @Override
            public void accept(DataChangedListener e) {
                e.notifyDataChanged();
            }
        });
    }
    private ItemService() {
        items = new LinkedList<>();
        listeners = new LinkedList<>();
        addItem(new Item(1,"Утюг", 2000, 5, "Хороший утюг"));
        addItem(new Item(2,"Чайник", 1200, 3, "Плохой чайник"));
        addItem(new Item(3,"Пылесос", 12000, 1, "Классный пылесос"));
        addItem(new Item(4,"Робот-пылесос", 30999, 4, "Рооооообот"));
        addItem(new Item(5,"Телевизор", 41500, 2, "LG"));
        addItem(new Item(6,"Холодильник", 50000, 2, "Холодит"));
    }
}
