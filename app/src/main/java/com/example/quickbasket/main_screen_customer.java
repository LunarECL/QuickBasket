package com.example.quickbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class main_screen_customer extends Activity implements StoreInfoRecyclerViewAdapter.OnNoteListener{
    private ArrayList<String> mStoreNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mLocations = new ArrayList<>();
    private ArrayList<String> mStoreIDs = new ArrayList<>();

    ArrayList<StoreOwner> owners = new ArrayList<>();
    int storeID;
    int customerID;
    DatabaseReference StoreOwnerData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_customer);

        //Get Customer ID from previous page
        Intent intent = getIntent();
        customerID = intent.getIntExtra("customerID", 0);

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButton_MainCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });

        //Intent intent = getIntent();
        //String StoreID = intent.getStringExtra("ID");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("StoreOwner").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    ArrayList storeIDMap = (ArrayList) task.getResult().getValue();
                    ArrayList<Map> storeList = (ArrayList<Map>) storeIDMap;
                    int index = 0;
                    //ERROR HERE
                    int check = 0;
                    for (Map<String, Object> entry : storeList){
                        if (entry != null) {
                            String storeName = String.valueOf(entry.get("Name"));
                            String location = String.valueOf(entry.get("Location"));
                            String logoURL = String.valueOf(entry.get("logoURL"));
                            String storeID = String.valueOf(index);

                            Log.d("storeIDS are ", storeID);
                            StoreOwner owner = new StoreOwner(storeName, location, logoURL);
                            owners.add(owner);
                            mStoreIDs.add(storeID);
                            index++;
                        }
                    }

                    initImageBitmaps();
                }
            }
        });

        //writeNewOwner("12", "ankit", "Fruits and Veggies", "Canada", "https://www.ryerson.ca/content/dam/international/admissions/virtual-tour-now.jpg");
    }

    public void writeNewOwner(String userID, String password, String storeName, String location, String logoURL) {
        StoreOwner owner = new StoreOwner(userID, password, storeName, location, logoURL);

        StoreOwnerData.child("StoreOwner").child(userID).setValue(owner);
    }

    private void initImageBitmaps() {
        if (owners != null){
            for (StoreOwner owner: owners){
                if (owner != null) {
                    mImageUrls.add(owner.logoURL);
                    mStoreNames.add(owner.storeName);
                    mLocations.add(owner.location);
                }
            }
        }


        /*mImageUrls.add(tempURL);
        mNames.add(tempName);
        mLocations.add(tempLocation);*/

        /*mImageUrls.add("https://media.istockphoto.com/photos/university-of-toronto-picture-id519685267?b=1&k=20&m=519685267&s=170667a&w=0&h=R45ZMm2Bf62gStoi01J6gQYDdZRBmuP9Oj5cWQpYAE4=");
        mNames.add("University of Toronto");
        mLocations.add("Toronto, ON");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/University_of_Waterloo_Math_3.jpg/1280px-University_of_Waterloo_Math_3.jpg");
        mNames.add("Waterloo University");
        mLocations.add("Waterloo, ON");

        mImageUrls.add("https://www.ryerson.ca/content/dam/international/admissions/virtual-tour-now.jpg");
        mNames.add("Ryerson University");
        mLocations.add("Toronto, ON");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/en/thumb/4/4e/University_of_Alberta_seal.svg/1200px-University_of_Alberta_seal.svg.png");
        mNames.add("Alberta University");
        mLocations.add("Edmonton, AB");

        mImageUrls.add("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFBcVFRUYGBcYGhkcGhkaGiIdHRwaHBkZHBkiIxwgICwjGiEoIhogJDUkKC0vMjIyGSI4PTgxPCwxMi8BCwsLDw4PHRERHDEpIygxMTMxOjEzMTMxMTExMTExMTExMzMvMTMxMTExMTExMTExMzExMTExMTExMTExMTExMf/AABEIAIcBdAMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAFAAIDBAYBBwj/xABGEAABAwIDBAcFBAkDAwQDAAABAgMRACEEEjEFQVFhBhMicYGRoTJCscHRFFLh8BUjM0NicoKSolOy8RbS4iRjc8KDk7P/xAAZAQADAQEBAAAAAAAAAAAAAAABAgMABAX/xAAwEQACAgEDAwIEBQQDAAAAAAAAAQIRAxIhMQRBUROhFGGBkSIyQlJxYrHB8QXR8P/aAAwDAQACEQMRAD8AerZgcJLQdJOk9Ui/8oFSP4PaSEDtOKSPuqB8wL16A2nKIA04VGrEq3Nr9I8xNeZHq5d6+x3PFDsvcwuF2riGxC23ZtqoTH9afrR3Ze01BJUpt5XeloEeIUPUCjhcCoBSoHf2VfGKhxWx2XIzJNuCinziJ8az6hSfBtCRnsR0jm//AKhsfwpaI/yBPrXMP0obTEqdXxzBM+SUxRpXRpiMoCxwIWbeZqq50ObIs44DxJB+VOssX2ZtK8lHEdLRm/VpWU8FN/RVvWrbPTBnRaHAd/ZEf7pqRvopl7SXSVDQLSCO6DNEjsxakjMUBY0OUHyMW8qWWRdkMlDuyPZW10vkhIy/dzakb+XkTRXq6Hfot2x60JI4ISfjb0ritmPlQP2oxOmUD/aRSKcu6ZpRg3tJe/8A0EurruSoRhVmApwwPujLPjM1YQ1lECT/ADEk+dUU34JOvIzJSyVMagLis0ZLfelMeUzRcqNyLLXCipiKaYrag0RxTTTyRyrmblSOaG0kRXSnuqWKUUNYdKIDPKouuQZGdMjWCLVOtoHWs7tXouHlT1pQOABPxVS696fAyjGvmGS4ACoqtrJ0HpVM7Ww8gFxF9L289KG4Xoayi5cdUf5svwFE17GbMTmMaSZ9SJ9ab1ILhtg0t8j0YxkjNnTHE2HhOtB8f0nZbVlSha+JSIH+UTRhGy2hEomON6nRhm0+y2kdwArLNFc7m9PwAWukKFeyy9HHLPokmiTWKKkhSW1QeNj5E1cDaBokCnUHmi+ArG0UC0pftSnut6zVXF7BacuRfiZPzowa5lNZZku4dMjLL6JIPv8A+P40z/otvev0/GtZlpZKouqa4YHBvlGWR0OaHA981KnowgaBof0E/FVaTJTC0rj6fWj8VL9wPT+SACuj6tziU9zaRTVdHln99/jRt1pX+qodyU/9tUMS0s6PqHmPgKyz2+Sii62BznRcq1dn+kfWov8AoxO9z/D8ato2co+1iVnuUfpTHNkLOjyyOavqqrxz1sn7EpY3LlIgHQxH+qr+0Uj0SZGrqz5Cmq6OOH98f7h8jXD0YI9p4z41VZ/6/Yi8T8IrYjYDCZ7ZPe4kemtDXMC2nSD3r+lE19Hkj98PGkOj6Y/ao9fpV4Zo+W/uSnifhewFLSNyUeZppRwCfKjidgo/1E+APzAqRWwQnir+WI+NV+IiS9FmbUg8vKolINaF3Z6U+75kfU1VWy2Pu+pqiyrsI8XlgbJSopmb4I8j9aVH1vkL6K8lRPSvGAR1xPePpFWGOmeKHtLKu63xBmsmcQrlXQ+rl5Gg8XTv9K+xzrJmXd/c3+G6fOAdsE/0g+oI+FE8N0zK7hxr+UpIP+SxXlwxB+6K6HydyahPpOnfCotHqMy5PW19IiBJxLSeRa+fWxV1rpZhgntvtqV/CI9CT8a8W6w8BS65X5FQl0ON/qZZdVPwj2odMGCQEFK/60p+JomjbTEAqdaTaYLibeM14F1yuI8q6nEq3gfCkfQxvaTX0GXVOt0vue9OdIsInXENeCwfQGoUdK8ETH2hHjmA8yI9a8O+1/wetL7WToE0y6KP7n9hfiZft9z3sbcwtv8A1DV//dT9asN45pXsuIV3LB+Br59Tilb0g+MVInEp3oPmKHwSfEvYPxT/AG+59AOYptPtLSnvUB8TTg4CJFxyM14El1vjHePpVzA4hSFZmnCg8UqKT6Vn/wAdJr8M1f8ABvjIp7pnt5d5VGXeXwryZzbeMIhTzkDnHqLnxrmG23imyClxXcolQ8iYrnl/xnU1+ZFF12Lwz1kujhTetHCvPWOmGJHtIaV4EH41ZV05A/dJ/wD2/wDjXHPoesi/y39S8eqwPubkr/M00rNYhrpqpXstNk8nPllq4x0wT+8ZUnmlQUPIwaWXRdWlen7MZdThbqzVZzXMxoMnpLhzo5/ir6VIrbjZEpcb/qzD0ia5ni6hcxf2ZZZMb4kvuFCTxphHM+VClbWtPW4f+8moXNvBIu6yf5QtXwFGODNLhP7Mzywjy0G8vfXFA7vhQBrpAlf71Ce9KkeqhFEWnllMoWlQ4+0PMGlnhyQ/Mq/lMeGSMvytMuBCuI8vxp+WqWJx5QkFS20zbMowJ8SPjQpfSVuY65qb6IJ01v1kUsYTlwNKVcmhUedV1LXPtpHLKfjNDDt5uBLzd+C0D5mqeM6WtNWJ608EQr/Idn1q0MGZ8RsWWWEVu0F3cSoavIT3pn6U1LrhEh6f5Wv+axG0OmDiv2TTaAd6kpWrygAetNwHTJYOV9tH/wAiG0+qYny8q7I9Fn03RB9Zh1VZtDjlpmVOK7mv/CoBtBKjCg6e8qHpYVTaxnWCW1skfwhM+lxTVYlUx16AeAWPrTR6d9xnlXYJHCtKiGQrvXf1NVHtmrPssNieaPmDTM7xsHlHuVNQllz76qeOKce6Fc7HK2O6R+zb/uT8k1H+hHt3V+Kj/wBtcLTv31+B+ld+xvHe55n61S2uZIWr7MSdh4g6dWPM/Kmq2A8dVJ8EqP8A9acNnPfx/wB341KjZL24kf1/jR9Sv1o2hv8ASyivYjidVxzyrHrlpqtmuC2ee5VFv0U6dV+ajVTENNt+2+0DwzyfIXqsMt90/oTnjreq+oO/Ry6eMEr7yR3z9K4vaeGSf2ilfyIUfVQSPWhmN6QmIabV/M4f/qk/OumOqX+qOacoR5f+Q7h2MpnrED+gn4pq0pIP72P5W4+EVhVbYxZNlkdyRHqJqu45iFmVOL/uI9LUzwSbtsl8TFbJM3q20zfEKnvj0z0q82Wwqbm/PWlTfDv9wnxX9PuSqw43rTy18KkOCTlCiuEngm8geW6jC9hIi6zzt859aa5spuICgfETU/VXkmv4AacITopJid8d9iI9ajUwRGa06cDFtQb0Z/RKSbE90/Kno2bEDNpMXHva/Gt66Q2kCBAG+Z31M2Gz7SlDha3jGlGE7IXIyi/cD+dan/Q7hvpO4JAofEwBpfYzikgGyvz5TTNa3yMLKFZwC4dIAAjQ6DKLcINUFbOv+zMcjPpIrfEwG9KRkOp8a71dbZvZ0CQVDgkpk/MetNWy5ByoExYlKfh+NJLqknsvcPpsx6WSd0+f0qZvDK0yn+2tIhjESCpKbX0QL+G7lUbLGIIAm0G6TqZ4zej8Svl9wPGwGrZ69SD5fhXPstt/dGtaVWFduVrF95MRPPxrrOEUJhxsHjInd9KHxUe4fTkZtGHWNFKHdNTdc4g+1mHMUbVhkpMrfRANxAOp57riuO4lhKZ6wqykHsge8T4HQ0y6uK4sDwt8gUrUrcSeU/CpkbPWqOzlHO3prV9zbrSQIzr0GsaBM7tbkeHOqp20CCpLQvvKib+k1vin4A8aXcX6HWbhM75BB+dqS2XEgytVr+0OXPnVHEbRUohcAAjd+Nzeq7ThWeyYmAJBuTwgGmXUSF0JlpeMcuAf8U/MV1GKd/1DPdbyFcdwyUgZ3U6AwAqY8QB61dXsZY1dSJO7LIGs3cG741lknJ0hnFRVukRfb3d6j/aj6VwbRcmyp8E/Srx2C1oXio395Cb8+2Y14VXdwjCSRmgp4PCDv3Jv3CKznlX+hag+6IF7Td3FH9o0qsvaDpPtgHgmAI8Bc0Vb2Q0tKcqQkmNFzJJyp1VYXEaa1XcwTYzJCyFSQMwi+nxB86nKcuHYySXAIcQpQzKVJsIJJNRpSQbCD3T8aLu7GWE5kqsL+z68qr4nBrQkKOUgzYG50oUFyYP6skmD8h5CupUtIt8flUilwLyPWo1ujgTOka+X50pk2LbJjiD9xI4zJOvM209TXOtMyUgjgJ+FOXg3I/ZLjjuqNDDivZbJvFtZplka7itfIep5BBzIg2iCPpamIxKhpPiZHwvVlvY+IV7iU/zECuv7JcbgqU1J0SFGZ4TEHzrLNWyYXG+UJvHJEFTcK4pMX+VWkbfcHsqdH/5VfOhCWXDEIPDWD4A601aFCQoKBFiIJjiJqjzatnT+wkY6eLX1ZoU9MH0RClE7wpST8UzVvD9OXwO00FndFvh9KyAWIBm3d+fyKe0FHQkc1WG7fzqE8eOXMV/YvDNkjxJ/c9Dw3SpRSCtvKreJVa5i+mkedJfSFxfsDKOJjx9tfyrBYdlxY7BkTEhXCunD4hOmYdy/zwqXpYk+EV+Jytbs02OxSlmHOtWdYKkxF7wFHnuocp21mTF7yfkKFrcxCiZBOYpJMi+UED88hUvXYghX6to5p90CN1oIt310wnCKqkQk5zfJZcdP+kB/dUf2oj3EetHcDiU6JbSgwCcylQTvgTGv5FX3m1hQKE5gd35/GnWZPhe4npy8mPXiT3HkAPlUS8QqdfWt8ygqELbgmRHVhQnmUgZdN531TxYUgkfZ21cyAmf8z8q3r/0+5vSl5MV1h5elKtYrEoEBWGvG4Jj40qPrL9pvSfkvoabbuhwAn+AD/aaa5jlAGXT3AKPpVVSlA5UtOLI/gUB5gU97CPXhtSdYJV8rW8a8hKct6Z0VHhEbmJCiMy1K72lH409GLUNAT/RHzoZiMK6kytwAQTAKTIHib1zDICkyVE30O7uoSTXLMg0raTo+6B/EPrXDtJwCVKQB3poJ9paC+rF1aRaJ513FKRAzFKUgp1Oi/A8aVR8jaiZfStPukqMHlvj8aqI6VLIT2ZucxvpaOQ4UKxDLWbLnN1BQywAO6bxVlpDQBSMystyBcwN5jdMeYq6x467s2tj0befdhLYheYmACeyEzECSbzXU9IHG1LKhKCSQCmMsiRBjgRTzhgE5wkBOZIBKhNxOg7U61BiNmIydZ1iEpUYTYlRIPa7IsBHHhTqGN7ULrlZVXtV1QAz2gJ74tM867hsQ4hOULIEmwPHL9PWii9jtobS4XEue8oCBDdtO1c3qJDiWil4JStpSiEo0VaJlV4IkaazwNPUeEgNyKWRxzsnrVJHMm8kzpGh9KssbJzXJSIIgKVM6TYHS2+Na1rIbkLW2MiyCjrYEJKZ1JteeMiKvv/ZEDtIQQdMqZta+nOg2kvA8McpbLkyuG2Kgky42jTdOkDjviKt4vYCFIhLiVkCPaCRad3ieFT/pHCFOdLTYEExa0LKRNxc8J+tZVzaPWJdggSpJSnhe4H3Ux8BS1J8G2jswvh8Eps5k4dokaH2uXvOAHyqI4Xrz1hDbMGBkSIMHtGELibi/8VW9oY9KG2urKS4oIKpBJukaCCkC/GaZ0Zwg6tfWFwCbQYE6akGDbXS9UjqezBashc2W3lJDxWqCTKTHIkx2RbdrVTZrbqFhQUlKAVXBIkxpOWx0rX4dhkyQZNwZdhMXkKIJJ7opEJFgpkwTAAWTB5m+7jVFCnsCWnwZXFuuKVmbW2RAHaOqimSbI4gwatfpBwNhpa4dJ9okgQRpBSB4xetB1cGShtOaw/V6xeBYlW8x30FawWLczrQiwK8o1JF4jKSIBtu08nepbpklGL7ewOxeEccVJUlN0hRgySRBk5ZuRMGq20WCFEC6oSFE65iLW1Fo9eNGcK1mSeuxC0OFUn9VmAkEZZJvcTpRDZ7LROZxxteWw7RClaGSSEjSezbv30vI/p1VIywwbvtIcSqMqSArTKBHlApNbOecSCchkzJkm/AgHWtUW8KpWZLjJBEFPXacbBwGdd9U8H1aXHB1maEnKkOtBOojVRIEA7pvrrTxhabbFaqtgacO6oJbU6ZA07UFIyjSL+IFSYnAdWkqWVBM2I5nded/CtFhNpMt9kuSqIzJUFqN98IRftU97GpdQnq3wlQNysFOYAHheJqUou+CixxauzKYfD4deZKgAkaZlkWEXmBx0qy6hptYbbgkEDsjMI1uvhbjvoo4eqMuFkiCcwVKrX9/XXgTTcTtBvKCmFnMBlygGCDr+rBIt60+l0SdXscTiUJUJQpYNoAIA0uSqrhSgyYay3gZr74I1118d1V3wjKcraUqQSCATcJ97Lz5CKjw4KhIQd4jeCNxG41w5cs4OqHTlsSYbEAZs7R1OWSlemh1tUG03EkJ6tKkytOYhGiJOa4FrRcU9sqWSlDalEGDG70pj+KQ0tKHIQpWgzA2PECSnxoQzz40g1S7l57EJMfrEp0N0JB8TAqhjsQmUEOpcJWE5QctjP3VcqOI2e2BN1HjYDyBnzNV8fi22YLh1FghGo5kk/HjV4uTdySK71uAHtjOqUQ28lQ9nKSoQdSIiJHKhWJbcbkZkqNjIIUm0Wvfwim49xSnFLbUoAqJFwCJ7u6KHuzpMfPjRSbJSq+DRs4xspAaS2DYqSRkCtM0XijTfUKQMywhQ3AyD6W7q8+D2U5gBIIIB3/UUcwu3msiytlJMiBKuU2tPnU3jrgaEvIdfQx/qDwOvmmqLCmFqUlBVKeBBJ423xU7G0cIo5RkkjRbcR/Ud/jUWMwsEFCUZLZhIkgn2gdCBrFI248r/AzSe51WCSbhV/CfHgahl5PsOEjmZNu/Tzp6mFpPZTPiZ8qaXzbM2QRraKSOtdzaV5LaNquAHOXEm1wqZPjoPOq5xZeUZzhcT2lovyBIA38aYhM6DyI+td/RbirhsHuKfrXRDLNPj2A4uiu/hVA3SSeOYfSuUWw2zlBICm7395PH+alXT6oNA5eGd/eOuWmQpwxcEaAkDWq5Thkk51tzvBInQDjO4HShyuj5KiXHJJnMFOC/gVfKuL2Oy2mVuNAa7jfwm9Qef/1C1Gt0yXamJZdSA25l6rMtSgjMIIE6GbE76gwWEaBX1uKQsezBSoCVJBE2ga86tYTZrCrocCv/AI0qJnwAveocRiQHOqQ247dGYwZCpAMiTdI46cqm5Ob2TMqXYsNbIUjMSllwJBIROUkxNjA46yNKF4tD8/s20CZhCm4zcyVa85NuNbRrCLHvnxcCvQJWKtIZtBSlXegfLL6imjD5FKTMpjiVoaWjqG3QO3kVJsex7AIiLkc67iMMlSUFKsilD9altteVRkwRJG62nGtUMK1/ppm8kpt6Kn0qZlCE2BSga2SR/tEnxp9E/KNTMZj9hpcWC2lxCYAy5Dc7zJsJ4Xp+J6MOOJSAgoyiyiofAAesmtbicUge2sDvMeljQle0cKg5svWG+oJH+ZAoaJ3yH02ygzsjqW1NLWhRiSgAKUeBykEgA3sN1BsdtEJytNJW2EquMxg8DlIG7lvoxnSpSnUNOIBBlxMqIEEdlNhv3aCu7POBQmHWFun7zjih5JCQB5nvqqwyfYSTjF03/knwe2cORAQsKAkwEQePvDfXMTthlba8sJUpCoCrKsnjHHnuots/CbLcb6w4dTWYnMF9YCN0zMCReRx5VR2oxspKQptsuqi0OLCRGkqVPkJpa3pIp+JRtNUYRC0hsyTmjsiDAOYnU8r24VNsppCsynUrKQUwEmJKiAdQeO6nltCFrN0pBKQArMb63O4G3hv1qFa1SQj3QCVDsyLKkkXm0eNZP8VEYyWrc1qhs8JSVIcSU5QMwVbhIn11q4Np4FKJaZEyfdWdNd/jrWOxbjikBKpsZlUEAQOAv43vVV9S0JTlUCAB7IIg6kx3zWc0uOSk5RT2NjhtqYZw/tFIOaT1rYPgk3IHqKI4N5psycSkpTEJQtSTMaRMRBGs156xiHAhTkHKVAZjEAkXidd/dVjBbQClEHU38hH0pnO3TAtKkuUbPa20m1rlK2nADMKZUb8cxUL6X5VQa2wtslTbTDc6qkk+pNAVYlUGDGsEJm/E+NSoZzBawtAOUmQ3JsAZUok/AfCm10qGnou+4TxO3HHOyt1vNFghIm/hJ86pIxrgJS4BIB7JSUq011qHCOszDoUpSRA7eUQR2pIFjvkHhRFQKnOtQ0R2MsJzRF7zqTfXfUnkWwvrVRlXO0sZANBIF4O/Wu4bEFuSUjUpkzF0giw3QfzpW32QhThUrI2gAi6kSojkN5tv5VBjsCS4paE5M0dlxuRYAbgRFpgiqqTkqEUW90ZxW1VwAkoJPBHlqatMuEqzKfDXZvaUzMcdedLE4btyG0ybqKBIJ0EDcI4Aa1WcwyMqsyFQYECQSomwE6aT4UXCTa32C3J7S4NNhtrJAhbTTovcrEx/VpPdVhO08L7+DSjfOQXIsO0E/kVmh0caLRcbdBCUkkKTJBO6ytezFLE4ctAqLjakxPZWSePsmlZaGl7NUG52YV9oqSbxdcCTJi24nhwqdbeHTkUnHLGUzlIzA309lJNrcqxSMQUgqvJM5bb9BpVxGN7WX7qSVKAAvN91Tk4N7i1A1CdqhT6EIU6ptRgJKLWTOpJUdNAKrbcxRaWgNs5gm8qEzN4A9oUCXicpStBJUkyADc2i5NhrRhnb64kHMOf4WpZyhHhP+Sc5JMubV2uhZQ0oLQVAHP8AcUpIy6HtXMd4q9sdf6vItKVFByZoHajfqaAYnaKFaNBJEmR2b/05fU1z9ISkIk9o+1JF80gx2jHdQU4t22CM97Cm3GG0NqcSkW3EDeQLGsQMUnUi5PDdeflWox2EW62Wk4pCwVZoy3MXMFMkjwoCvYDqRKShYEkwpPwN6dRXMQt2R4ctLKQSQSSLCw4b9/dRD9GtadYlPelU+iSKBnEZFWGlx38aL4faKCg5wSopMd+46W475FLK+zBTsu4XBBNkPIMWFwDbmqJqZOGVJEJJF4lMie40O2JiMrboXe6CBYZgJzQog5Ztu8aqK2kQpWcRKeyI37vDnU5Y23zYzTQeQt1IhKCBwiR9KuN4dZRLhQjfYyoiN4mBQAbbV1asqETIhQSJGniZjjvqxjttKCyAElJTvM9ogTcWsfyaeONLkVbF47PWTYII4mRPdY/Sm/ZXBq3pplKfmUkVSbxeJQkhbaiRooKN7p3AxpNTI2u5aW1+yk6T2iqI0kfG1U0R8MyLHVE3Lbk930MUqdidtIbUUFQURqYGv91Kh6ePyNqD7GycIL6nitSvhahHSjYYdCQzlASCQlAgKVa5VlMwLXO/zhZxWIbguLEEWBSCTvmBB3byPSmvdIVFXazBP8I1vu7QI/up2oLuBSdbor7Mxb2HhhsCZuBvVFzmkVfa2y7bMykyVAQFSSmQq8mSINB/tiC91gVlhQVChNtCLTu+NFMFtFoqbhYlK3FXtZeciJiTcWp46WtmW1wLiekumZoieCvqm9TK6RtxZC54WA85+VRYRQlq/shwHgJIj4VmljRKpEGRNrjThvFFxrd8D6o1aDqtvuLs22PAFfroPGq768StJUpagAD2Qb/2osfE1nlbTdczAnqxFpM3tHZEACJpmAWr2ZOYalJiinC6/uScpeUv4QdwjKDlkulYJMrb6sRebhRJHeKurDaPZcRmgXQjMSd/aOl9wigzGPOYpUkuKiwKjbfOvD40axOMbThypKkIPV5ImFHTNYCx8azzdl2F0xTbbtgDEJxnWFSV5kz2SokW7pIndruri/tCgEFxSlg6JJUc06QbTHwotgNvtNtgrazrnUq922vdMUN2ttR1xSQtxATMoDSMmWRx3mOZFqm80XH5g1Vujj7yiUNOqdSuxAVmChPEDjv3WqN/BOBGSEFAj9YVQBygXk8DwruNxinFZkoAtrqoxfW0UOLrhlJsDu/PdUHOV3F7GlPV3HYiUkoXBlIAI0M7/P51xG1LZARYiBpN9O7vrS4Do6jENtOZoABStCvaJG9KiIym3reaI4vZKWEDqm0CZlQuRpFwD5mq0nFN9hVDazM4/G5koSVqjhAGWL2O/dUmzNkBwSHApKtQBJKQdCLQT5WqLFbIMgoOdMXifUElXjUCWltrkhITEFCTHoYmhCcFWwNKls3RoX9l4eMhkJtZTqTB3QEyocxNDH9lYcSWlrSrSMuZH+RCvUd1WMJhVKWMqkRGbQmwMe8I1+dE8LsEqElYUDzt3W+tXf4lVJFIxgnu2zLONPpRCgrKbgAyg7pBBgmoUtvdlvKQpXsg2kSN5kRNenYHAJbSEJQlCdyUzHqSaEdKV9SEqzBEmMyj46Qd03rnyJxW24mhrcxf2VSEELBzkwRI3Dl3nfurW9HXSMPCFIzpURCyqABvsscY3aVmce8lTilpMgiRedwTYc7m/Gq+ExqhmAUQmLjjpu8vKlg2tzLZnorOKXJSso7OUKI0zKMJg5iIJtBvzq0EmSfn8q8zVtJQzAGxUSeZOv1qyjpA4HUOLKjAg8xcR6zxqmpvsUjI0/SLaKW0EdWCo6KUmw8xc1kn9qlZAhKdBAkAGbmCT+RUu3tth7sAKGSdTrI4afk0BSuVRNrek76DNKTui4VqCsgJuNOJ3GKWIUG1JSoCfeg68pvUOIdUlxKpOnPx7vwq27s+RKhc8FGfGQaGqKW7FdlYrlYHE/DlUWGacUtKUDMtRgJT2ja+6bXPlV7BYIZwMxTA7KjuV5HTjbwrW4To+pGV0dpQTcIOUkkRmSrQazFFRTFlxaAmG2A4Y60ttgSMq1STw9kEDzm2lGf+nW0pT1a86zJIQUgGBNiVCY7haiX2haRBddSODzaXB3Zkaa76SHAo/smXDEy2vKrW3ZUPnVVirYRZIVTRUXsRtCwXC5l3AoJ5wopHZERqBMmujZuHN20sr3x1qknW4g6xw+FWy4BcqxDR350hweYm3jXeuS4LOMO8lWPkZjyoeml+kKcP0tfVDsTg0II6ppbYAupOUzx0v60MfwyTbrch4KQU+tXjhIghlaI/0nCBH9KgPSmrxRzEdapA+66AoDxKR/urOEWqtoen4T/hmWd6KOFRU2pDgvZKgTfviagx+xsQAlJaICBACQeMmSJk+Na0PtJu6GnDbtNIywbm/b7tJq3h8YwbJCgb2CVXMTYZbk0jxeGarPPU7PdCAENLKhOZW48BruvUbOzyoEulSCkSnsntHgSYAnjNekYx/KkKLimkk265laRpxMXtxpqEpUCpbjJTc5kHdygyfOnWJ8sXVHyeeYRS3FJSEibBOayBG4ncDwrrqEIVlc9pJuEqkT37+61bBzY7S1505wJkJX2gecG4nhIpj/R9tWoE/ncZFK8bWy2MiBjaycoltxKLdqxEAW0k1xSxiUlDKikgiVadmL393yqltXAvpSlCEuKQnT2THcARaodl4JSCVFAUVC6FEpIPHQGfGL0n4267Bs5tzCQ6UoUjKkAe8ZOqiTGsk0qrO7JfKiQm0nd+J+NKhpn4CajEN9YwF+8i06SOP551nnGzugQOQ/GtRjWyhtDaUkiJMAm9uFDWejjrpJQFxxMAd0lQ8q0otyC1a2M45N5jz31UShZMgeOlafauyBhcpdCSpUlIkHS16bsd1pRUVJCiEkgQALHnAvNBP8VCcOgcxhX1CUKKDvXGo395rq8WUnK4rOsE9ox2e7860Sb2mXipCiGhx4d3Ghj+zEpVmDjbm4AmPEzTTkpRozbZTwGFCnFKXmIMiBqSeEGeelT4YoaUslHs6AmZtMxltAt41J9k6sHK42Z0GY2nX3aSsH1pGd1FtQlCifOBU2pSbXZm3J30obSH0/vRYHcR9Kr4vGpWicqFLPZKlJlQBm43DffWj2IwzQwic687SFBOZPtEz4caGYNlg9ptsLvfM4T5pEUzxaXqsOmTA2GYUqxGYwYgTrejDOyXYBcCG08XDl8k+16VfGKWAUoCUCDZpMHS19fWqakdoFwknhMnxO6lcbA41yWW8I0IlTjpOgSMiTu3yo+ApynUt+yhts7sqc6/7lTFUHcUqIAyjgkR5q+XrVcKB1I7t3jvNBJIGpLhBo4hJcB65XZuFgKmY3KJ+ECi+F2sskAuNuH+MAHzSQT4zWXSEfdBPKpU5d9uU1roKytdjdKwrOXM+Ggs6JQQokd5+sc6gVh2NEhB5daU/KKxsD3T6Cl1qx7x8z9azkOsy7xNa7s5Ez9lCjxzZ9O5J9aajaqW4T1IQBuAy25ZhHpWXRtFxO8/D4Cats9JHR76vl5Emk1S8jLLDsq+gae2yha/1aHOHtkg/wBKfpTcQwYJUlMeGpm3p6UPb6TEHtJQf6QT55RFUdp7TU4FBsNoEWhKZ1GsXOtZJS2C8seb9int7BBN0AX1A15/H0oDh0EhRG7lutRZbj5TFjb2oywN+o0qmsKA/WDIbQN0GL+VdEb8E9SfAKWrtGLX/PnT0rWIVeJJmiasC0sGFpCpETaZjwrqJQcpTcWhQB9DRlJJWF0gLPPUXHdTsNEz5GK0+HwHWJKy0hLSJzLIKRziDBPIA61OhnAtq7IKo95Y7PgkfOaykmC9gRhMF1pENqJG9BIjzBAHlRzDbECYzOK/kAzK8SDHjPhRDElKR1ZVqiRBAAkWsYAJm2lQYbC4jK2tQQ02gXdcV2lCxMDdMb6DjFc7mim3uFcPsFtFyme7tGe+Mg8AauoQmSAcpG7MSvlabDuAFDBjkOOtKQ4SP1nsXSeybKI00kaXFMddUtCiUtvEOlIFgQneCT7wv6UkM10mqOqWJK2uxY2XtQNuLzJbWqBARlUZvrJ7UacRQ7bG2XHVwsJUBMIUMxniIICDuiimAebSvIEkEqXECUkJJsZEAxxq1h8K0SS3kBzKkphNzqCbeQrpjngc0sE3sCMMgwLutHelKpHkqandYUqSssuj/wB1vKdfvCR6VbxDjaAQgdYocLJ1jXf6aUE2k8VzmM7oGgoS6hLgy6RPuWup6s2ZdRzZczDT7hPypK2mZAGJSNRkfbKTM27Ry/CgYAZMJddbAvYlSbW9nwonh9ouKH7Rp0cFCD6W86f1ItWReCadJl9ClkSrDNOD7zKxHkQKhW61eA7hzIupu0iSII17wapqxDSZLmGLZF8zSvUBJHlerLO0mzZvGEE+66AeNpUEncd5qiUJK0ycnkg2nZYbedcUmMW06BuzEHxTNX28IAcziGk5blZCbfMngBxobicMpQksMO80qyk34kR61TVh0+ylvEIMjsFSlNGx3yRTaLAs226TObR24lDyUMoUUkA3lRMlQzcYkd3xq5h9qBISHSlKlE6qj/isztfHDriCq6UjVIudbJUDI0EjhTXHG1t51pUSnRKJCjeUkXKQDwy8a5pS/E0johelNmswm1m3Zg6a3keYqyCk7xQno9s1tTefOpvN2sqkAQd5kGFDnAqj0gWlAKA4lwSASg3BvII/Gmb0xtjbs0qG0ETIpVisBtNxCSBcEzryH0rtJ6oKNnslOHSlKnldYo+6tcJEW9gi/wDVNENqdLG20IyBMqISMpz5ZGpA0AG/SsOkxv8AmafmH/NJ6sV+n3MlLiwV0hxLmIczStW6Tw5DcOQqHC4FwgSnzMUb6ydAK4pU1OUnLsHSV2cHpJ+lWRs8b485+HdUrDWYi2nHSj+F2OtQkgIT95VvJOvnFJHHbGUF3AAwKALC+8/jQ7E4xKLNlJNwSACBuN5ub+nlr9rbBccAQ06ALTIIVAG6PaFtSRbjQZGzsHhv2rn2hxPuJgIGvtHTvkqNXjFR3kZ0hr2BWrZjLbSFLK3SYQmZSS4QY90aX51RY2SlqVPuDOLdUmFLiABK/ZQR3mrGP6ROODIghtvQIbsI5q1V6DlQoAnXTgNPKtKd8CSn4Lb+0EQUNNJbTvglSlcMyz7XcABVFeZWp8N1TBA311LZ3VNk7s4huNTHx8qeZNotzvTwY58ZifrXUIB1sOU0AjUJTvA/PdUuGRCsyZBv73H1pyGgNJ+NOUobotx/GigUNccicyQZBFkgbuJBM85qiAqAZ9frFWVpP/H4VAEcfWgw2NIcPGOV6S8RFgL05SY7+HDv+n5LFLUN586WgMtqbbEApVMXINp36j8xzrjrYEhKplAVe0doDXeLTNVUPlJtqeAifGiWzsP1qiCBksFK+8oEEAE7rRRSTZtNvYk2Zgs6ZNm5B/mKZM93D/mo9sPpWNJQmAkc9Jp20NoqXnabgAW3xYFP40OxLClNhBIzACTup9TrSuCyikthr2zQpIF0gaK+XdRvZezCGwrEJBMjq0mc2XmdQk8DVrYuCASHnBYewk+8RvPECrGOdVlLliZ0O/kO78KrBUrkM1ZV2ziD1RmAIhKY7IHdutWLU9JPeLVoto7VDjSkQOyUkGTobHvid/E+GdwiLknjv5XFJNp7iuOxexDys0qMkgX31A8vdeN3InfBrry5uPz9KlDYOmsacqi3p3FYKwmIdZcKmzCuI0N7SNDW1axKXGkS0UlS0rVqntbyDHa799Z9sFCgpMSNJAUPIyKL4zbPXthtzM3xU1ae9Mg+So5UynCTt8jxytKg3gic2bMQJX2SNZUSDUrDxTmBbSApSvZOtpBNtTB8qxDGyXU9ph8rInspknxQYUd2iSK0GBD7iQQgyhMKJBjMEwTcATy1FI8LlxujqjmjsyTF4tKeyTFvYT/NqT+dTQ99lxyUgkEyUhIlViTp3Coi8ltOVCJULFRuBBOh33v41LsTbBYWtZb61SgLyQQmbxYgAkjyFdscKXO5yyySfGwPxLTgPbKkqiO2kom5Pdvqu0/CjZNt8W5+zrW6w/S7CuAhaVo3HMAof4yfSqe1l4NwtJbKFFxwBRQAFBNpGnZJkajcaaUYad+DQlO9h3UCBOuUE/1DMPQirez8gbUlTaVpKiYUkKFwBooRuqZ1KFOKBWApRJCTYxqANxAFDUdYCerdbWPumJ9L1OMlxEeUXzIvfYMLMhvqzxbKm/8A+agN28UP2mApKktPLIFlIXrHEKygqHnPGnrxjqT22p/lPy1qi64HHJykCB7QgyKtCTIzxrkixL7aA0h2TmbUAIkGVOIESbKFvShGA2Yta0NwpDaYUuRBUfLwEaCTvq90mc6pphQSk3dHaSFdodWoa3GpNqpbO6Uq9laUnW4G7mKzcXLcXTKvkaZW0GzlS2+hOWQUEC40iFQbEcayOKS1Ci2IHaOUzMTYmb7+J0PKr4xiHQSCkzPZWAY8xbwNCcSsaxp5H8/SpZlw/JoSvbwT4THvIEIUsJJJgacPlSob144VypFKNIHeFTMyqlSpKQyLjeEJsnKZ01GvhR5jo6AJcVl3nLc6caVKq44p2F8BXCMNoIS2gZiNTcnnPDxHdQ7am322VZe044IlI7IE6Soj/aO+lSqk9lsTk2Y/H9IHHc14Sq5SmUp0i41VbjQ0qKtbilSqBLkekjhUiVbgKVKgwjzbW5pilfndSpUpiVtSib+VShcd9dpUxhilk603rNwpUqDCNIpnWHcTSpUDIapUCoVLpUqDMT4TDlbnVix99XBO8Dn+e8o7i8ieqQIgkDkJuSd9KlTcIeADweHUFOSbE68RPCtLsPAh5QSbJSVFU3MAxAM+G/XkKVKmjwysUGsSrOoBNkiyRwG760I6SOFKUpHsnd3XmuUqfJ+Uxk82VZjtAi4P53a1AlWW3OlSqaEQkLNxw/Iq+yAUoJkGBJnT6/jSpUmTgMUmWFoExymeNcUzzpUq5SbSsnwGzCvtE5Ui0635CjS3HAjqy4tSOBM/HdypUq9Hp4qgxKrjIIggAVLsPZMZ1JWUCUITvusgnmPZG/fSpVaXA0eSuNlqQ6tTqULbJWQTcglci2thwqmtlKMS3kEIC2hqTcqBOt94pUqlPv8AwUhx9TYpeObLlMahUiDbhqKothpY7HbTJnOCohW8AquB3WpUqXpJN3fhA6qKVV5Z1WGiySpJ3BKyBPccw9Kp4h9xsfrIjitIjzbVPiU0qVdulUcEss1VMHdI8ck4NILYUkunRWoyzIJSCDIG7jWcwbjeRxSEklIlIWBbdu10ndrSpVzJt8/M7JLZfQhOKV/D3BKR8BaoVPZkzuvNq7SqQsSNs2pUqVYc/9k=");
        mNames.add("University of British Columbia");
        mLocations.add("Vancouver, BC");
        */
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.store_list_recycle_view);
        StoreInfoRecyclerViewAdapter adapter = new StoreInfoRecyclerViewAdapter(this, mStoreNames, mImageUrls, mLocations, this, storeID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, StoreDetailCustomerPage.class);
        Log.d("Value of ID is ", mStoreIDs.get(position));
        intent.putExtra("ID", mStoreIDs.get(position));
        startActivity(intent);
    }
}
