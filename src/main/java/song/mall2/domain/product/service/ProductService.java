package song.mall2.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import song.mall2.domain.common.dto.PageDto;
import song.mall2.domain.img.dto.ImageDto;
import song.mall2.domain.img.dto.UploadFileDto;
import song.mall2.domain.img.entity.Image;
import song.mall2.domain.img.repository.ImageJpaRepository;
import song.mall2.domain.img.service.FileService;
import song.mall2.domain.product.dto.*;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.exception.invalid.exceptions.InvalidUserException;
import song.mall2.exception.notfound.exceptions.ProductNotFoundException;
import song.mall2.exception.notfound.exceptions.UserNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpaRepository productRepository;
    private final UserJpaRepository userRepository;
    private final OrderProductJpaRepository orderProductRepository;
    private final ImageJpaRepository imageRepository;
    private final FileService fileService;

    @Transactional
    public ProductDto saveProduct(Long userId, SaveProductDto saveProductDto) {
        User user = getUser(userId);

        UploadFileDto thumbnailUpload = getThumbnail(saveProductDto.getThumbnail());

        Product product = Product.create(user, saveProductDto.getName(), saveProductDto.getPrice(), saveProductDto.getDescription(),
                thumbnailUpload.getFileUrl(), saveProductDto.getStockQuantity(), saveProductDto.getCategoryName());

        Product saveProduct = productRepository.save(product);
        log.info("size ==================== {}", saveProductDto.getImgList().size());
        saveImgList(saveProductDto.getImgList(), saveProduct);

        List<ImageDto> imgDtoList = getImgDtoList(saveProduct.getId());
        return new ProductDto(saveProduct.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getStockQuantity(), product.getCategory().name(),
                product.getUser().getName(), imgDtoList, false, true);
    }

    @Transactional
    public ProductDto getProduct(Long productId) {
        Product product = findById(productId);

        List<ImageDto> imgDtoList = getImgDtoList(product.getId());

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getStockQuantity(), product.getCategory().name(),
                product.getUser().getName(), imgDtoList);
    }

    @Transactional
    public ProductDto getProduct(Long productId, Long userId) {
        Product product = findById(productId);
        User user = getUser(userId);

        List<ImageDto> imgDtoList = getImgDtoList(product.getId());

        boolean isPurchased = isPurchased(user.getId(), product);
        boolean isSeller = product.isSeller(user.getId());

        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDescription(),
                product.getThumbnailUrl(), product.getStockQuantity(), product.getCategory().name(),
                product.getUser().getName(), imgDtoList, isPurchased, isSeller);
    }

    @Transactional
    public ProductDto editProduct(Long productId, Long userId, SaveProductDto saveProductDto) {
        User user = getUser(userId);
        Product product = findById(productId);
        if (!product.isSeller(user.getId())) {
            throw new InvalidUserException("접근 권한이 없습니다.");
        }

        if (!product.getThumbnailUrl().substring(product.getThumbnailUrl().lastIndexOf("/")).equals(saveProductDto.getThumbnail().getOriginalFilename())) {
            UploadFileDto newThumbnailUpload = getThumbnail(saveProductDto.getThumbnail());
            product.update(saveProductDto.getName(), saveProductDto.getPrice(), saveProductDto.getDescription(),
                    saveProductDto.getStockQuantity(), newThumbnailUpload.getFileUrl(), product.getCategory().name());
        } else {
            product.update(saveProductDto.getName(), saveProductDto.getPrice(), saveProductDto.getDescription(),
                    saveProductDto.getStockQuantity(), product.getCategory().name());
        }

        Product saveProduct = productRepository.save(product);

        updateImageList(saveProductDto.getImgList(), saveProduct);

        boolean isPurchased = isPurchased(saveProduct.getUser().getId(), saveProduct);
        List<ImageDto> imgDtoList = getImgDtoList(saveProduct.getId());

        return new ProductDto(saveProduct.getId(), saveProduct.getName(), saveProduct.getPrice(), saveProduct.getDescription(),
                saveProduct.getThumbnailUrl(), saveProduct.getStockQuantity(), saveProduct.getCategory().name(),
                saveProduct.getUser().getName(), imgDtoList, isPurchased, true);
    }

    @Transactional
    public PageDto findProductList(Pageable pageable) {
        Page<ProductListDto> pageDto = productRepository.findAll(pageable)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));


        return new PageDto(pageDto);
    }

    @Transactional
    public PageDto findProductListBySearch(Pageable pageable, String searchValue) {
        Page<ProductListDto> pageDto = productRepository.findAllBySearch(pageable, searchValue)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));
        return new PageDto(pageDto);
    }

    @Transactional
    public PageDto findProductListByCategory(Pageable pageable, String categoryName) {
        Product.Category category = Product.Category.of(categoryName);
        Page<ProductListDto> pageDto = productRepository.findAllByCategory(pageable, category)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));
        return new PageDto(pageDto);
    }

    @Transactional
    public PageDto findProductListBySearchAndCategory(Pageable pageable, String searchValue, String categoryName) {
        Product.Category category = Product.Category.of(categoryName);

        Page<ProductListDto> pageDto = productRepository.findAllBySearchAndCategory(pageable, searchValue, category)
                .map(product -> new ProductListDto(
                        product.getId(), product.getName(), product.getPrice(), product.getThumbnailUrl(),
                        product.getUser().getName()
                ));
        return new PageDto(pageDto);
    }

    private void saveImgList(List<MultipartFile> imgList, Product saveProduct) {
        if (imgList == null || imgList.isEmpty()) {
            return;
        }
        imageRepository.saveAll(fileService.upload(imgList)
                .stream()
                .map(fileDto -> Image.create(saveProduct, fileDto.getFileUrl()))
                .toList());
    }

    private List<ImageDto> getImgDtoList(Long productId) {
        return imageRepository.findAllByProductId(productId)
                .stream()
                .map(image -> new ImageDto(image.getFileUrl()))
                .toList();
    }

    private void updateImageList(List<MultipartFile> editImgList, Product saveProduct) {
        if (editImgList == null) {
            return;
        }
        List<Image> productImageList = saveProduct.getImageList();
        List<String> productImageNameList = productImageList.stream()
                .map(Image::getStoredName)
                .toList();
        List<String> editImgNameList = editImgList.stream()
                .map(MultipartFile::getOriginalFilename)
                .toList();

        imageRepository.deleteAll(productImageList.stream()
                .filter(image -> !editImgNameList.contains(image.getStoredName()))
                .toList());

        List<MultipartFile> newImageFile = editImgList.stream()
                .filter(multipartFile -> !productImageNameList.contains(multipartFile.getOriginalFilename()))
                .toList();

        saveImgList(newImageFile, saveProduct);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private UploadFileDto getThumbnail(MultipartFile thumbnail) {
        return fileService.upload(thumbnail);
    }

    private Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }

    private boolean isPurchased(Long userId, Product product) {
        return orderProductRepository.findAllByProductIdAndUserId(product.getId(), userId).stream()
                .anyMatch(orderProduct -> orderProduct.getProduct().getId().equals(product.getId()));
    }
}
