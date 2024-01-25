package song.mall2.domain.img.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.mall2.domain.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String fileUrl;
    private String storedName;

    private Image(Product product, String fileUrl) {
        this.product = product;
        this.fileUrl = fileUrl;
        this.storedName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.lastIndexOf("."));
    }

    public static Image create(Product product, String fileUrl) {
        Image image = new Image(product, fileUrl);
        product.addImage(image);
        return image;
    }
}
