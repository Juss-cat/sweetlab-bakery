package util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Clase de utilidades para manejar imágenes en la aplicación
 */
public class ImagenesUtil {
    
    // Cache de imágenes para evitar cargar múltiples veces
    private static final Map<String, Image> imagenCache = new HashMap<>();
    
    /**
     * Obtiene una imagen del directorio de recursos
     */
    public static Image obtenerImagen(String ruta) {
        if (imagenCache.containsKey(ruta)) {
            return imagenCache.get(ruta);
        }
        
        try {
            Image imagen = new Image(new File(ruta).toURI().toString());
            imagenCache.put(ruta, imagen);
            return imagen;
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + ruta);
            return null;
        }
    }
    
    /**
     * Obtiene una ImageView redimensionada
     */
    public static ImageView obtenerImageView(String ruta, double ancho, double alto) {
        Image imagen = obtenerImagen(ruta);
        if (imagen == null) return null;
        
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(ancho);
        imageView.setFitHeight(alto);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    /**
     * Obtiene la ruta de una imagen de producto
     */
    public static String rutaImagenProducto(int idProducto) {
        return "resources/images/products/producto_" + idProducto + ".png";
    }
    
    /**
     * Obtiene la ruta del logo de la tienda
     */
    public static String rutaLogo() {
        return "resources/images/ui/logo.png";
    }
    
    /**
     * Obtiene la ruta de un icono
     */
    public static String rutaIcono(String nombre) {
        return "resources/images/ui/icon_" + nombre + ".png";
    }
    
    /**
     * Verifica si una imagen de producto existe
     */
    public static boolean existeImagenProducto(int idProducto) {
        File archivo = new File(rutaImagenProducto(idProducto));
        return archivo.exists();
    }
    
    /**
     * Limpia el cache de imágenes
     */
    public static void limpiarCache() {
        imagenCache.clear();
    }
    
    /**
     * Obtiene una imagen con un tamaño predefinido
     */
    public static ImageView obtenerImagenProducto(int idProducto, double tamanio) {
        String ruta = rutaImagenProducto(idProducto);
        if (!existeImagenProducto(idProducto)) {
            // Usar imagen por defecto
            ruta = "resources/images/ui/producto_default.png";
        }
        return obtenerImageView(ruta, tamanio, tamanio);
    }
}
