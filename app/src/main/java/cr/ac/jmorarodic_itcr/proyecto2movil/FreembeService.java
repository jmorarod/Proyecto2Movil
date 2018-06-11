package cr.ac.jmorarodic_itcr.proyecto2movil;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.AuthUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Category;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CommentJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CreatedUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.EmailPost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.FavoriteJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Subcategory;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.TelephonePost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;

import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Category;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FreembeService {

    @GET("categories")
    Call<List<Category>> obtenerCategorias(@Header("Authorization") String token);

    @GET("subcategories")
    Call<List<Subcategory>> obtenerSubcategorias(@Header("Authorization") String token);


    @POST("categories")
    Call<Category> crearCategoria(@Header("Authorization") String token, @Query("name") String name, @Query("description") String description, @Query("photo") String photo);


    @POST("authenticate")
    Call<AuthUser> iniciarSesion(@Query("email") String email, @Query("password") String password);

    @POST("users")
    Call<CreatedUser> crearUsuario(@Query("name") String name, @Query("email") String email, @Query("password") String password, @Query("photo") String photo, @Query("type") String role);

    @GET("announcements")
    Call<List<Announcement>> obtenerAnuncios(@Header("Authorization") String token);

    @GET("users/{id}")
    Call<User> obtenerUsuarioId(@Header("Authorization") String token, @Path("id") int id);

    @GET("randomannouncement")
    Call<Announcement> obtenerAnuncioRandom(@Header("Authorization") String token);

    @GET("categorybyname")
    Call<List<Category>> obtenerCategoriaPorNombre(@Header("Authorization") String token, @Query("name") String name);

    @GET("announcementbyname")
    Call<List<Announcement>> obtenerAnuncioPorNombre(@Header("Authorization") String token, @Query("name") String name);

    @GET("announcements/{id}")
    Call<Announcement> obtenerAnuncioId(@Header("Authorization") String token, @Path("id") int id);

    @GET("categories/{id}")
    Call<Category> obtenerCategoriaId(@Header("Authorization") String token, @Path("id") int id);

    @GET("announcementsubcategory")
    Call<List<Announcement>> obtenerAnunciosPorSubcategoria(@Header("Authorization") String token, @Query("subcategory_id") int id);

    @GET("announcementsubcategoryP")
    Call<List<Announcement>> obtenerAnunciosPorSubcategoriaP(@Header("Authorization") String token, @Query("subcategory_id") int id);

    @POST("favorites")
    Call<FavoriteJson> crearFavorito(@Header("Authorization") String token, @Query("user_id") int user_id, @Query("announcement_id") int announcement_id);

    @DELETE("favorites/{id}")
    Call<FavoriteJson> eliminarFavorito(@Header("Authorization") String token, @Path("id") int id);

    @POST("telephones")
    Call<TelephonePost> crearTelefonoUsuario(@Header("Authorization") String token, @Query("user_id") int user_id, @Query("telephone") String telephone);

    @POST("emails")
    Call<EmailPost> crearEmailUsuario(@Header("Authorization") String token, @Query("user_id") int user_id, @Query("email") String email);

    @PUT("telephones/{id}")
    Call<TelephonePost> editarTelefonoUsuario(@Header("Authorization") String token, @Path("id") int id, @Query("telephone") String telephone);

    @PUT("ueditarfoto")
    Call<User> editarFotoUsuario(@Header("Authorization") String token, @Query("user_id") int user_id, @Query("photo") String photo);

    @PUT("emails/{id}")
    Call<EmailPost> editarEmailUsuario(@Header("Authorization") String token, @Path("id") int id, @Query("email") String email);

    @POST("announcements")
    Call<Announcement> crearAnuncio(@Header("Authorization") String token, @Query("title") String title, @Query("description") String description, @Query("price") float price, @Query("photo") String photo, @Query("user_id") int user_id, @Query("latitude") float latitude, @Query("longitude") float longitude, @Query("subcategory_id") int subcategory_id, @Query("place") String place);

    @POST("comments")
    Call<CommentJson> crearComentario(@Header("Authorization") String token, @Query("user_id") int user_id, @Query("announcement_id") int announcement_id, @Query("description") String description);

}

