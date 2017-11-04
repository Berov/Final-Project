package com.artgallery.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Berov on 28.10.2017 г..
 */

public class DBManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "artdb";
    private static final int DB_VERSION = 1;
    private static Context context;
    private static ArrayList<Item> itemsAdd = new ArrayList<>();
    private static final String SQL_CREATE_USERS = "CREATE TABLE Users(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "email TEXT NOT NULL UNIQUE," +
            "password TEXT NOT NULL," +
            "name TEXT NOT NULL," +
            "address TEXT NOT NULL," +
            "phone TEXT NOT NULL," +
            "image BLOB," +
            "wallet REAL," +
            "flag_sold INTEGER," +
            "isAdmin INTEGER" +
            ");";

    private static final String SQL_CREATE_TYPES = "CREATE TABLE Types(" +
            "id INTEGER PRIMARY KEY," +
            "type TEXT NOT NULL UNIQUE" +
            ");";

    private static final String SQL_CREATE_SUBTYPES = "CREATE TABLE Subtypes(" +
            "id INTEGER PRIMARY KEY," +
            "subtype TEXT NOT NULL," +
            "type_id INTEGER NOT NULL," +
            "FOREIGN KEY (type_id) REFERENCES Types (id)" +
            ");";

    private static final String SQL_CREATE_ITEMS = "CREATE TABLE Items(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT NOT NULL," +
            "price REAL NOT NULL," +
            "description TEXT NOT NULL," +
            "picture BLOB NOT NULL," +
            "owner_id INTEGER NOT NULL," +
            "author TEXT," +
            "subtype_id INTEGER NOT NULL," +
            "FOREIGN KEY(owner_id) REFERENCES Users(id)," +
            "FOREIGN KEY(subtype_id) REFERENCES Subtypes(id)" +
            ");";

    private static final String SQL_PUT_ADMIN = "INSERT INTO Users ('email', 'password', 'name', 'address', 'phone', 'isAdmin')" +
            "VALUES ('berov@ittalents.bg', 'qwerty', 'Berov', 'Sofia, Bulgaria, Infinity Tower, floor 14', '+359885107407', 1 );";

    private static final String SQL_PUT_TYPES = "INSERT INTO Types('id', 'type') VALUES" +
            "(1, 'graphics')," +
            "(2, 'painting')," +
            "(3, 'sculpture')," +
            "(4, 'wood carving')," +
            "(5, 'ceramics')," +
            "(6, 'glassware')," +
            "(7, 'textiles')," +
            "(8, 'iconography');";

    private static final String SQL_PUT_SUBTYPES = "INSERT INTO Subtypes('id', 'subtype', 'type_id') VALUES" +
            "(1, 'linocut', 1)," +
            "(2, 'screen printing', 1)," +
            "(3, 'etching', 1)," +
            "(4, 'aquarelle', 2)," +
            "(5, 'oil painting', 2)," +
            "(6, 'acrylic painting', 2)," +
            "(7, 'charcoal', 2)," +
            "(8, 'pencil painting', 2)," +
            "(9, 'pastel', 2)," +
            "(10, 'stone', 3)," +
            "(11, 'metal', 3)," +
            "(12, 'gypsum', 3)," +
            "(13, 'classic wood carving', 4)," +
            "(14, 'modern carving', 4)," +
            "(15, 'interior carving', 4)," +
            "(16, 'souvenir woodcarving', 4)," +
            "(17, 'wall panel', 5)," +
            "(18, 'souvenir ceramics', 5)," +
            "(19, 'household ceramics', 5)," +
            "(20, 'stained glass', 6)," +
            "(21, 'household glass sculpture', 6)," +
            "(22, 'souvenir glass', 6)," +
            "(23, 'jewelery', 6)," +
            "(24, 'clothes', 7)," +
            "(25, 'accessories', 7)," +
            "(26, 'textile panel', 7)," +
            "(27, 'carpets', 7)," +
            "(28, 'new style', 8)," +
            "(29, 'old style', 8);";

    private static DBManager ourInstance;

    public static DBManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DBManager(context);
            DBManager.context = context;
        }
        return ourInstance;
    }

    private DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
//        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS);
        sqLiteDatabase.execSQL(SQL_CREATE_TYPES);
        sqLiteDatabase.execSQL(SQL_CREATE_SUBTYPES);
        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS);
        sqLiteDatabase.execSQL(SQL_PUT_ADMIN);
        sqLiteDatabase.execSQL(SQL_PUT_TYPES);
        sqLiteDatabase.execSQL(SQL_PUT_SUBTYPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //TODO if necessary
    }

    public void addUser(User u) {
        if (userExists(u.getEmail())) {
            Toast.makeText(context, "E-mail " + u.getEmail() + " already exists!", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues(); //map from keys and values
        values.put("email", u.getEmail());
        values.put("password", u.getPassword());
        values.put("name", u.getName());
        values.put("address", u.getAddress());
        values.put("phone", u.getPhoneNumber());
        long id = getWritableDatabase().insert("Users", null, values);
        u.setId((int) id);
        Toast.makeText(context, "Registration success!", Toast.LENGTH_SHORT).show();
    }

//    private static void loadUsers() {
//        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT id, email, password, name, address, phone, isAdmin FROM Users", null);
//        while (cursor.moveToNext()) {
//            String email = cursor.getString(cursor.getColumnIndex("email"));
//            String password = cursor.getString(cursor.getColumnIndex("password"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String address = cursor.getString(cursor.getColumnIndex("address"));
//            String phone = cursor.getString(cursor.getColumnIndex("phone"));
//
//            User u = new User(name, email, password, phone, address);
//
//            u.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            u.setAdmin(cursor.getInt(cursor.getColumnIndex("isAdmin")) == 1);
//        }
//        cursor.close();
//    }

    public void updateUser(User u) {
        ContentValues values = new ContentValues();
        values.put("name", u.getName());
        values.put("password", u.getPassword());
        values.put("address", u.getAddress());
        values.put("phone", u.getPhoneNumber());
        values.put("flag_sold", u.getSaleFlagAsInteger());
        values.put("wallet", u.getWallet());

        if (u.getUserImageBytes() != null) {
            byte[] data = u.getUserImageBytes();
            values.put("image", data);
        }

        getWritableDatabase().update("Users", values, "id=" + u.getId(), null);
    }

    public void deleteUser(User u) {
        //TODO later
    }

    public boolean isValidLogin(String email, String password) {
        boolean valid = false;
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT email, password FROM Users", null);
        while (cursor.moveToNext()) {
            String registeredEmail = cursor.getString(cursor.getColumnIndex("email"));
            String registeredPassword = cursor.getString(cursor.getColumnIndex("password"));
            if (registeredEmail.equals(email) && registeredPassword.equals(password)) {
                valid = true;
                break;
            }
        }

        cursor.close();

        return valid;
    }

    public boolean userExists(String newEmail) {

        boolean exists = false;
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT email FROM Users", null);

        while (cursor.moveToNext()) {
            String email = cursor.getString(cursor.getColumnIndex("email"));
            if (newEmail.equals(email)) {
                exists = true;
                break;
            }
        }

        cursor.close();
        return exists;
    }

    public User getUser(String enteringEmail, String enteringPassword) {
//        User registeredUser = null;
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT  id, email, password FROM Users", null);
        Integer id = null;

        while (cursor.moveToNext()) {
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            if (enteringEmail.equals(email) && enteringPassword.equals(password)) {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                break;
            }
        }

        cursor.close();
        return getUserByID(id);
//
//        cursor = ourInstance.getWritableDatabase().rawQuery("SELECT email, password, name, address, phone,image,wallet, isAdmin FROM Users WHERE id=?", new String[]{id + ""});
//
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//        }
//
//        String name = cursor.getString(cursor.getColumnIndex("name"));
//        double wallet = cursor.getDouble(cursor.getColumnIndex("wallet"));
//        String email = cursor.getString(cursor.getColumnIndex("email"));
//        String password = cursor.getString(cursor.getColumnIndex("password"));
//        String phone = cursor.getString(cursor.getColumnIndex("phone"));
//        String address = cursor.getString(cursor.getColumnIndex("address"));
//        byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
//        int flagSold = cursor.getInt(cursor.getColumnIndex("flag_sold"));
//
//        registeredUser = new User(name, email, password, phone, address, wallet, flagSold);
//        registeredUser.setId(id);
//        registeredUser.setUserImageBytes(image);
//
//        if (cursor.getInt(cursor.getColumnIndex("isAdmin")) == 1) {
//            registeredUser.setAdmin(true);
//        }
//
//        cursor.close();
//        return registeredUser;
    }


    public User getUserByID(Integer userID) {

        User user = null;
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT email, password, name, address, phone, image ,wallet, flag_sold, isAdmin FROM Users WHERE id=?", new String[]{userID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        String name = cursor.getString(cursor.getColumnIndex("name"));
        double wallet = cursor.getDouble(cursor.getColumnIndex("wallet"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String phone = cursor.getString(cursor.getColumnIndex("phone"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
        int flagSold = cursor.getInt(cursor.getColumnIndex("flag_sold"));
        int isAdmin = cursor.getInt(cursor.getColumnIndex("isAdmin"));

        user = new User(name, email, password, phone, address, wallet, flagSold);
        user.setId(userID);
        user.setUserImageBytes(image);

        if (isAdmin == 1) {
            user.setAdmin(true);
        }

        cursor.close();
        return user;
    }


    public int addNewItem(Item item) {

        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("price", item.getPrice());
        values.put("description", item.getDescription());
        values.put("picture", item.getBytePicture());
        values.put("owner_id", item.getOwnerID());
        if (!item.getAuthor().isEmpty()) {
            values.put("author", item.getAuthor());
        }
        values.put("subtype_id", item.getSubtypeID());

        long id = getWritableDatabase().insert("Items", null, values);
        item.setId((int) id);

        Toast.makeText(context, "An Item is uploaded\n" +
                "title: " + item.getTitle() + "\n" +
                "description: " + item.getDescription() + "\n" +
                "price: " + item.getPrice() + "\n" +
                "author: " + item.getAuthor() + "\n" +
                "owner id: " + +item.getOwnerID() + "\n" +
                "", Toast.LENGTH_SHORT).show();

        return (int) id;
    }

    private static void loadItems() {
//        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT id, title, description, price, author, owner_id, user_id picture FROM Items", null);
//        while (cursor.moveToNext()) {
//            String title = cursor.getString(cursor.getColumnIndex("title"));
//            String description = cursor.getString(cursor.getColumnIndex("description"));
//            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
//            String author = cursor.getString(cursor.getColumnIndex("author"));
//            Integer owner_id = cursor.getInt(cursor.getColumnIndex("owner_id"));
//            Integer user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
//            byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
//
//            Item item = new Item(user_id, title, description, price, author, owner_id, picture);
//            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            itemsAdd.add(item);
//        }
//        cursor.close();
    }

    public ArrayList<String> getTypes() {
        ArrayList<String> types = new ArrayList<>();
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT type FROM Types", null);
        while (cursor.moveToNext()) {
            types.add(cursor.getString((cursor.getColumnIndex("type"))));
        }
        cursor.close();
        return types;
    }

    public ArrayList<String> getSubtypesByType(String type) {
        ArrayList<String> subtypes = new ArrayList<>();
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT subtype FROM Subtypes WHERE type_id=" + getTypeID(type), null);
        while (cursor.moveToNext()) {
            subtypes.add(cursor.getString(cursor.getColumnIndex("subtype")));
        }
        cursor.close();
        return subtypes;
    }

    private int getTypeID(String type) {
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT id FROM Types WHERE type =?", new String[]{type + ""});
        int id = -100;
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return id;
    }

    public int getSubtypeID(String subtype) {

        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT id  FROM Subtypes WHERE subtype=?", new String[]{subtype + ""});
        int id = -100;

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }

        cursor.close();
        return id;
    }

    public void updateUserWallet(User user, double transfer) {

        ContentValues values = new ContentValues();
        values.put("wallet", transfer);
        getWritableDatabase().update("Users", values, "id=" + user.getId(), null);
    }

    public double getUserWallet(int userID) {

        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT wallet FROM Users WHERE id = ?", new String[]{userID + ""});
        cursor.moveToNext();
        double money = cursor.getDouble(cursor.getColumnIndex("wallet"));
        cursor.close();

        return money;
    }

    public ArrayList<Item> getItemsBySubtype(String subtype, int userID) {
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT * FROM Items WHERE subtype_id = " + getSubtypeID(subtype) + " AND owner_id !=" + userID, null);
        return getItemsByCursorQwery(cursor);
    }

    public ArrayList<Item> getAllItems(int userID) {
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT * FROM Items WHERE owner_id !=" + userID, null);
        return getItemsByCursorQwery(cursor);
    }

    public ArrayList<Item> getItemsBySearchWord(String search, int userID) {
        Cursor cursor = ourInstance.getWritableDatabase().rawQuery("SELECT * FROM Items WHERE owner_id !=" + userID + " AND (description like '%" +
                search + "%' OR title like '%" +
                search + "%' OR author like '%" +
                search + "%')", null);
        return getItemsByCursorQwery(cursor);
    }

    private ArrayList<Item> getItemsByCursorQwery(Cursor cursor) {

        ArrayList<Item> items = new ArrayList<>();
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            byte[] picture = cursor.getBlob(cursor.getColumnIndex("picture"));
            int owner_id = cursor.getInt(cursor.getColumnIndex("owner_id"));
            int subtype_id = cursor.getInt(cursor.getColumnIndex("subtype_id"));
            String author = "";

            if (cursor.getString(cursor.getColumnIndex("author")) != null) {
                author = cursor.getString(cursor.getColumnIndex("author"));
            }

            Item item = new Item(id, subtype_id, title, description, price, author, owner_id, picture);
            items.add(item);
        }

        cursor.close();
        return items;
    }

    public void updateItem(Item item) {

        new AsyncTask<Item, Void, Void>() {

            @Override
            protected Void doInBackground(Item... items) {
                Item item = items[0];
                ContentValues values = new ContentValues();
                values.put("owner_id", item.getOwnerID());
                getWritableDatabase().update("Items", values, "id=" + item.getId(), null);
                return null;
            }
        }.execute(item);
    }

    public void soldSeen(int ownerID) {// ??????????????????????????????????????????????????????????????????
        ContentValues values = new ContentValues();
        values.put("flag_sold", 0);
        getWritableDatabase().update("Users", values, "id=" + ownerID, null);
    }

}
