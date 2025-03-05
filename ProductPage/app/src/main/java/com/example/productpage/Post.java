package com.example.productpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Request code for image selection

    private Spinner itemSpinner;
    private EditText captionInput, priceInput, descriptionInput;
    private ImageView selectedImageView;
    private Uri selectedImageUri; // Store selected image URI

    // Firebase references
    private FirebaseFirestore db;
    private StorageReference storageReference;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Initialize Firebase services
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        // Initialize UI elements
        itemSpinner = findViewById(R.id.itemSpinner);
        captionInput = findViewById(R.id.captionInput);
        priceInput = findViewById(R.id.priceInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        selectedImageView = findViewById(R.id.selectedImageView); // Use correct ID
        Button addImageButton = findViewById(R.id.addImageButton);
        Button postButton = findViewById(R.id.postButton);

        // Populate Spinner
        String[] items = {"Fan", "Trunk", "Cycle", "Books", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        itemSpinner.setAdapter(adapter);

        // Add Image Button Action
        addImageButton.setOnClickListener(v -> openImagePicker());

        // Post Button Action
        postButton.setOnClickListener(v -> uploadImageToFirebase());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            selectedImageView.setImageURI(selectedImageUri);
        }
    }

    private void uploadImageToFirebase() {
        // Validate user input
        String caption = captionInput.getText().toString().trim();
        String price = priceInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String selectedItem = itemSpinner.getSelectedItem().toString();

        if (caption.isEmpty() || price.isEmpty() || description.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique file name and upload image
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpg");

        fileRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot ->
                fileRef.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String imageUrl = task.getResult().toString();
                        savePostToFirestore(selectedItem, caption, price, description, imageUrl);
                    }
                })
        ).addOnFailureListener(e ->
                Toast.makeText(PostActivity.this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
        );
    }

    private void savePostToFirestore(String item, String caption, String price, String description, String imageUrl) {
        Map<String, Object> postData = new HashMap<>();
        postData.put("item", item);
        postData.put("caption", caption);
        postData.put("price", price);
        postData.put("description", description);
        postData.put("imageUrl", imageUrl);

        db.collection("posts").add(postData).addOnSuccessListener(documentReference -> {
            Toast.makeText(PostActivity.this, "Post Uploaded Successfully!", Toast.LENGTH_SHORT).show();
            resetForm();
        }).addOnFailureListener(e ->
                Toast.makeText(PostActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show()
        );
    }

    private void resetForm() {
        captionInput.setText("");
        priceInput.setText("");
        descriptionInput.setText("");
        selectedImageView.setImageResource(0);
        selectedImageUri = null;
        itemSpinner.setSelection(0);
    }
}
