# 🎁 Carpeta de Promociones

## Estructura de archivos para promociones

Esta carpeta está destinada a almacenar las **imágenes de las promociones** de Sweet Lab Bakery.

### 📁 Estructura recomendada:

```
promotions/
├── banner_descuento_1.png      (Banner de promoción principal)
├── banner_descuento_2.png      (Banner de promoción secundaria)
├── promo_postres_dulces.png    (Promoción de postres)
├── promo_bebidas_verano.png    (Promoción de bebidas)
├── promo_combos.png            (Promoción de combos)
└── promo_seasonal.png          (Promociones estacionales)
```

### 🎨 Recomendaciones para las imágenes:

1. **Tamaño**: 400x300px o 500x350px (para que se vea bien en la UI)
2. **Formato**: PNG o JPG
3. **Nombre**: Use nombres descriptivos en minúsculas con guiones
4. **Resolución**: Mínimo 150dpi para calidad

### 📝 Cómo usarlas en el código:

```java
Image promoBanner = new Image("resources/images/promotions/nombre_promo.png");
ImageView imageView = new ImageView(promoBanner);
imageView.setFitWidth(400);
imageView.setFitHeight(300);
imageView.setPreserveRatio(true);
```

### 💡 Tipos de promociones recomendadas:

- ✨ Descuentos por cantidad
- 🍰 Ofertas por tipo de producto (postres, bebidas, comidas)
- 🎉 Promociones especiales (cumpleaños, días festivos)
- 💝 Paquetes/Combos
- 📅 Promociones estacionales

¡Coloca tus imágenes aquí y estarán listas para usar!
