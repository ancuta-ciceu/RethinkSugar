package com.example.rethinksugar.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity(tableName = "CategoryItems")
data class CategoryItems(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,

    @ColumnInfo(name = "idcategory")
    @Expose
    @SerializedName("id")
    var idcategory: Int,

    @ColumnInfo(name = "namecategory")
    @Expose
    @SerializedName("name")
    val namecategory: String,

    @ColumnInfo(name = "flavorcategory")
    @Expose
    @SerializedName("flavor")
    val flavorcategory: String,

    @ColumnInfo(name = "descriptioncategory")
    @Expose
    @SerializedName("description")
    val descriptioncategory: String,

    @ColumnInfo(name = "imageUrl")
    @Expose
    @SerializedName("imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "ingredients")
    @Expose
    @SerializedName("ingredients")
    var ingredients: List<String>,

    @ColumnInfo(name = "recipe")
    @Expose
    @SerializedName("recipe")
    val recipe: String
)

