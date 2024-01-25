package song.mall2;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.mall2.domain.cart.entity.Cart;
import song.mall2.domain.cart.repository.CartJpaRepository;
import song.mall2.domain.favorite.entity.Favorite;
import song.mall2.domain.favorite.repository.FavoriteJpaRepository;
import song.mall2.domain.orderproduct.entity.OrderProduct;
import song.mall2.domain.order.entity.Orders;
import song.mall2.domain.order.repository.OrderProductJpaRepository;
import song.mall2.domain.orderproduct.repository.OrdersJpaRepository;
import song.mall2.domain.payment.entity.Payment;
import song.mall2.domain.payment.repository.PaymentJpaRepository;
import song.mall2.domain.product.entity.Product;
import song.mall2.domain.product.repository.ProductJpaRepository;
import song.mall2.domain.review.entity.Review;
import song.mall2.domain.review.repository.ReviewJpaRepository;
import song.mall2.domain.user.entity.User;
import song.mall2.domain.user.entity.UserRole;
import song.mall2.domain.user.repository.UserJpaRepository;
import song.mall2.domain.user.repository.UserRoleJpaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static song.mall2.domain.user.entity.UserRole.Role.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Init {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.setData();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final UserRoleJpaRepository userRoleRepository;
        private final ProductJpaRepository productRepository;
        private final CartJpaRepository cartRepository;
        private final OrdersJpaRepository ordersRepository;
        private final OrderProductJpaRepository orderProductRepository;
        private final PaymentJpaRepository paymentRepository;
        private final ReviewJpaRepository reviewRepository;
        private final FavoriteJpaRepository favoriteRepository;

        @Transactional
        public void setData() {
            User userA = saveUser("a", "a", "nameA", "test1@email.com");
            grantRoleUser(userA);
//            grantRoleSeller(userA);
            User lmood = saveUser("lmood", "lmood", "엘무드", null);
            User lkmost = saveUser("lkmost", "lkmost", "라이크 더 모스트", null);
            User mktim = saveUser("mtkim", "mtkim", "matinKim", null);
            User eightseconds = saveUser("eightseconds", "eightseconds", "8seconds", null);
            User northfc = saveUser("northfc", "northfc", "north face", null);
            User theory = saveUser("theory", "theory", "theory", null);
            User glw = saveUser("glw", "glw", "굿라이프웍스", null);

            Product north1 = saveProduct(northfc, "눕시 자켓", 3900, "<p>north face 남성 에코 눕시 자켓</p><br/> <img src=/file/downloadFile/northfc1-1.jpg><br/> <img src=/file/downloadFile/northfc1-2.jpg><br/> <img src=/file/downloadFile/northfc1-3.jpg><br/>", "/file/downloadFile/northfc1-thumbnail.jpg", 150, Product.Category.OUTER);
            Product north2 = saveProduct(northfc, "눕시 온볼 자켓", 2400, "<p>north face 남성 눕시 온볼 자켓</p><br/> <img src=/file/downloadFile/northfc2-1.jpg><br/> <img src=/file/downloadFile/northfc2-2.jpg><br/> <img src=/file/downloadFile/northfc2-3.jpg><br/> <img src=/file/downloadFile/northfc2-4.jpg><br/>", "/file/downloadFile/northfc2-thumbnail.jpg", 150, Product.Category.OUTER);
            Product north3 = saveProduct(northfc, "화이트라벨 다운 코트", 3500, "<p>north face 고 프리 화이트라벨 다운 자켓</p><br/> <img src=/file/downloadFile/northfc3-1.jpg><br/> <img src=/file/downloadFile/northfc3-2.jpg><br/> <img src=/file/downloadFile/northfc3-3.jpg><br/> <img src=/file/downloadFile/northfc3-4.jpg><br/>", "/file/downloadFile/northfc3-thumbnail.jpg", 150, Product.Category.OUTER);
            Product north4 = saveProduct(northfc, "핫샷", 1300, "<p>north face 핫샷</p><br/> <img src=/file/downloadFile/northfc4-1.jpg><br/> <img src=/file/downloadFile/northfc4-2.jpg><br/>", "/file/downloadFile/northfc4-thumbnail.jpg", 150, Product.Category.ACCESSORY);
            Product north5 = saveProduct(northfc, "미니 샷", 890, "<p>north face 미니 샷</p><br/> <img src=/file/downloadFile/northfc5-1.jpg><br/> <img src=/file/downloadFile/northfc5-2.jpg><br/>", "/file/downloadFile/northfc5-thumbnail.jpg", 150, Product.Category.ACCESSORY);
            Product north6 = saveProduct(northfc, "보레알레스2", 1250, "<p>north face 보레알레스2</p><br/> <img src=/file/downloadFile/northfc6-1.jpg><br/> <img src=/file/downloadFile/northfc6-2.jpg><br/>", "/file/downloadFile/northfc6-thumbnail.jpg", 150, Product.Category.ACCESSORY);
            Product north7 = saveProduct(northfc, "베이직 라운드티 화이트", 250, "<p>north face 코튼 베이직 반팔 라운드티 화이트</p><br/> <img src=/file/downloadFile/northfc7-1.jpg><br/> <img src=/file/downloadFile/northfc7-2.jpg><br/>", "/file/downloadFile/northfc7-thumbnail.jpg", 150, Product.Category.TOP);
            Product north8 = saveProduct(northfc, "베이직 라운드티 블랙", 250, "<p>north face 코튼 베이직 반팔 라운드티 블랙</p><br/> <img src=/file/downloadFile/northfc8-1.jpg><br/> <img src=/file/downloadFile/northfc8-2.jpg><br/>", "/file/downloadFile/northfc8-thumbnail.jpg", 150, Product.Category.TOP);
            Product north9 = saveProduct(northfc, "티볼 머플러", 590, "<p>north face 티볼 머플러</p><br/> <img src=/file/downloadFile/northfc9-1.jpg><br/> <img src=/file/downloadFile/northfc9-2.jpg><br/>", "/file/downloadFile/northfc9-thumbnail.jpg", 150, Product.Category.ACCESSORY);
            Product north10 = saveProduct(northfc, "파워스트레치 글러브", 250, "<p>north face 키즈 논슬립 파워스트레치 글러브</p><br/> <img src=/file/downloadFile/northfc10-1.jpg><br/> <img src=/file/downloadFile/northfc10-2.jpg><br/> <img src=/file/downloadFile/northfc10-3.jpg><br/>", "/file/downloadFile/northfc10-thumbnail.jpg", 150, Product.Category.ACCESSORY);

            Product theory1 = saveProduct(theory, "men hobeart payton pants black", 3450, "<p>theory men hobeart payton pants black</p><br/> <img src=/file/downloadFile/theory1-1.jpg><br/> <img src=/file/downloadFile/theory1-2.jpg><br/> <img src=/file/downloadFile/theory1-3.jpg><br/> <img src=/file/downloadFile/theory1-4.jpg><br/> <img src=/file/downloadFile/theory1-5.jpg><br/>", "/file/downloadFile/theory1-thumbnail.jpg", 70, Product.Category.BOTTOM);
            Product theory2 = saveProduct(theory, "men knowledge payton pants melange", 3450, "<p>theory men hobeart payton pants black</p><br/> <img src=/file/downloadFile/theory2-1.jpg><br/> <img src=/file/downloadFile/theory2-2.jpg><br/> <img src=/file/downloadFile/theory2-3.jpg><br/> <img src=/file/downloadFile/theory2-4.jpg><br/> <img src=/file/downloadFile/theory2-5.jpg><br/>", "/file/downloadFile/theory2-thumbnail.jpg", 150, Product.Category.BOTTOM);

            Product mtkim1 = saveProduct(mktim, "accordion wallet in black", 880, "<p>마뗑킴 accordion wallet in black</p><br/> <img src=/file/downloadFile/mtkim1-1.jpg><br/> <img src=/file/downloadFile/mtkim1-2.jpg><br/> <img src=/file/downloadFile/mtkim1-3.jpg><br/>", "/file/downloadFile/mtkim1-thumbnail.jpg", 10, Product.Category.ACCESSORY);
            Product mtkim2 = saveProduct(mktim, "accordion wallet in white", 880, "<p>마뗑킴 accordion wallet in white</p><br/> <img src=/file/downloadFile/mtkim2-1.jpg><br/> <img src=/file/downloadFile/mtkim2-2.jpg><br/> <img src=/file/downloadFile/mtkim2-3.jpg><br/>", "/file/downloadFile/mtkim2-thumbnail.jpg", 10, Product.Category.ACCESSORY);
            Product mtkim3 = saveProduct(mktim, "accordion wallet in green", 880, "<p>마뗑킴 accordion wallet in green</p><br/> <img src=/file/downloadFile/mtkim3-1.jpg><br/> <img src=/file/downloadFile/mtkim3-2.jpg><br/> <img src=/file/downloadFile/mtkim3-3.jpg><br/>", "/file/downloadFile/mtkim3-thumbnail.jpg", 10, Product.Category.ACCESSORY);
            Product mtkim4 = saveProduct(mktim, "buckle bag", 900, "<p>마뗑킴 buckle bag</p><br/> <img src=/file/downloadFile/mtkim4-1.jpg><br/> <img src=/file/downloadFile/mtkim4-2.jpg><br/> <img src=/file/downloadFile/mtkim4-3.jpg><br/>", "/file/downloadFile/mtkim4-thumbnail.jpg", 10, Product.Category.ACCESSORY);
            Product mtkim5 = saveProduct(mktim, "sporty tote bag", 1400, "<p>마뗑킴 sporty tote bag</p><br/> <img src=/file/downloadFile/mtkim5-1.jpg><br/> <img src=/file/downloadFile/mtkim5-2.jpg><br/> <img src=/file/downloadFile/mtkim5-3.jpg><br/>", "/file/downloadFile/mtkim5-thumbnail.jpg", 10, Product.Category.ACCESSORY);
            Product mtkim6 = saveProduct(mktim, "mini buckle bag", 900, "<p>마뗑킴 mini buckle bag</p><br/> <img src=/file/downloadFile/mtkim6-1.jpg><br/> <img src=/file/downloadFile/mtkim6-2.jpg><br/> <img src=/file/downloadFile/mtkim6-3.jpg><br/>", "/file/downloadFile/mtkim6-thumbnail.jpg", 10, Product.Category.ACCESSORY);

            Product glw1 = saveProduct(glw, "와이드 데님 팬츠 그레이", 420, "<p>굿 라이프 웍스 이지 와이드 데님 팬츠 그레이</p><br/> <img src=/file/downloadFile/glw1-1.jpg><br/> <img src=/file/downloadFile/glw1-2.jpg><br/> <img src=/file/downloadFile/glw1-3.jpg><br/> <img src=/file/downloadFile/glw1-4.jpg><br/>", "/file/downloadFile/glw1-thumbnail.jpg", 100, Product.Category.BOTTOM);
            Product glw2 = saveProduct(glw, "와이드 데님 팬츠 블랙", 420, "<p>굿 라이프 웍스 이지 와이드 데님 팬츠 블랙</p><br/> <img src=/file/downloadFile/glw2-1.jpg><br/> <img src=/file/downloadFile/glw2-2.jpg><br/> <img src=/file/downloadFile/glw2-3.jpg><br/> <img src=/file/downloadFile/glw2-4.jpg><br/>", "/file/downloadFile/glw2-thumbnail.jpg", 100, Product.Category.BOTTOM);
            Product glw3 = saveProduct(glw, "세미 와이드 코듀로이 팬츠 크림", 390, "<p>굿 라이프 웍스 에센셜 세미 와이드 코듀로이 팬츠 크림</p><br/> <img src=/file/downloadFile/glw3-1.jpg><br/> <img src=/file/downloadFile/glw3-2.jpg><br/> <img src=/file/downloadFile/glw3-3.jpg><br/> <img src=/file/downloadFile/glw3-4.jpg><br/>", "/file/downloadFile/glw3-thumbnail.jpg", 100, Product.Category.BOTTOM);
            Product glw4 = saveProduct(glw, "카라 케이블 니트 집업 블랙", 590, "<p>굿 라이프 웍스 카라 케이블 니트 집업 블랙</p><br/> <img src=/file/downloadFile/glw4-1.jpg><br/> <img src=/file/downloadFile/glw4-2.jpg><br/> <img src=/file/downloadFile/glw4-3.jpg><br/> <img src=/file/downloadFile/glw4-4.jpg><br/>", "/file/downloadFile/glw4-thumbnail.jpg", 50, Product.Category.OUTER);
            Product glw5 = saveProduct(glw, "브이넥 니트 베스트 그레이", 390, "<p>굿 라이프 웍스 유니섹스 브이넥 니트 베스트 그레이</p><br/> <img src=/file/downloadFile/glw5-1.jpg><br/> <img src=/file/downloadFile/glw5-2.jpg><br/> <img src=/file/downloadFile/glw5-3.jpg><br/> <img src=/file/downloadFile/glw5-4.jpg><br/> <img src=/file/downloadFile/glw5-5.jpg><br/>", "/file/downloadFile/glw5-thumbnail.jpg", 50, Product.Category.TOP);
            Product glw6 = saveProduct(glw, "오버사이즈 솔리드 셔츠 오프화이트", 390, "<p>굿 라이프 웍스 오버사이즈 옥스포드 원포켓 솔리드 셔츠 오프화이트</p><br/> <img src=/file/downloadFile/glw6-1.jpg><br/> <img src=/file/downloadFile/glw6-2.jpg><br/> <img src=/file/downloadFile/glw6-3.jpg><br/> <img src=/file/downloadFile/glw6-4.jpg><br/>", "/file/downloadFile/gl6-thumbnail.jpg", 50, Product.Category.TOP);

            Product lmoodP1 = saveProduct(lmood, "화란 니트 인디핑크", 100, "<p>엘무드 화란 니트 인디핑크</p><br/> <img src=/file/downloadFile/lmood1-1.jpg><br/> <img src=/file/downloadFile/lmood1-2.jpg><br/> <img src=/file/downloadFile/lmood1-3.jpg><br/> <img src=/file/downloadFile/lmood1-4.jpg><br/> <img src=/file/downloadFile/lmood1-5.jpg><br/>", "/file/downloadFile/lmood1-thumbnail.jpg", 100, Product.Category.TOP);
            Product lmoodP2 = saveProduct(lmood, "화란 니트 칸초", 100, "<p>엘무드 화란 니트 칸초</p><br/> <img src=/file/downloadFile/lmood2-1.jpg><br/> <img src=/file/downloadFile/lmood2-2.jpg><br/> <img src=/file/downloadFile/lmood2-3.jpg><br/> <img src=/file/downloadFile/lmood2-4.jpg><br/>", "/file/downloadFile/lmood2-thumbnail.jpg", 100, Product.Category.TOP);
            Product lmoodP3 = saveProduct(lmood, "화란 니트 애쉬 그레이", 100, "<p>엘무드 화란 니트 애쉬 그레이</p><br/> <img src=/file/downloadFile/lmood3-1.jpg><br/> <img src=/file/downloadFile/lmood3-2.jpg><br/> <img src=/file/downloadFile/lmood3-3.jpg><br/> <img src=/file/downloadFile/lmood3-4.jpg><br/>", "/file/downloadFile/lmood3-thumbnail.jpg", 100, Product.Category.TOP);
            Product lmoodP4 = saveProduct(lmood, "에센셜 후드 크림", 200, "<p>엘무드 에센셜 후드 크림</p><br/> <img src=/file/downloadFile/lmood4-1.jpg><br/> <img src=/file/downloadFile/lmood4-2.jpg><br/>", "/file/downloadFile/lmood4-thumbnail.jpg", 200, Product.Category.TOP);
            Product lmoodP5 = saveProduct(lmood, "에센셜 후드 블랙", 200, "<p>엘무드 에센셜 후드 블랙</p><br/> <img src=/file/downloadFile/lmood5-1.jpg><br/> <img src=/file/downloadFile/lmood5-2.jpg><br/>", "/file/downloadFile/lmood5-thumbnail.jpg", 200, Product.Category.TOP);
            Product lmoodP6 = saveProduct(lmood, "에센셜 후드 멜란지", 200, "<p>엘무드 에센셜 후드 멜란지</p><br/> <img src=/file/downloadFile/lmood6-1.jpg><br/> <img src=/file/downloadFile/lmood6-2.jpg><br/>", "/file/downloadFile/lmood6-thumbnail.jpg", 200, Product.Category.TOP);
            Product lmoodP7 = saveProduct(lmood, "오디너리 가디건 블랙", 250, "<p>엘무드 오디너리 크롭 가디건 블랙</p><br/> <img src=/file/downloadFile/lmood7-1.jpg><br/> <img src=/file/downloadFile/lmood7-2.jpg><br/>", "/file/downloadFile/lmood7-thumbnail.jpg", 150, Product.Category.OUTER);
            Product lmoodP8 = saveProduct(lmood, "오디너리 가디건 멀티 차콜", 250, "<p>엘무드 크롭 가디건 멀티 차콜</p><br/> <img src=/file/downloadFile/lmood8-1.jpg><br/> <img src=/file/downloadFile/lmood8-2.jpg><br/>", "/file/downloadFile/lmood8-thumbnail.jpg", 150, Product.Category.OUTER);
            Product lmoodP9 = saveProduct(lmood, "에센셜 맨투맨 드라이 라벤터", 150, "<p>엘무드 맨투맨 드라이 라벤터</p><br/> <img src=/file/downloadFile/lmood9-1.jpg><br/> <img src=/file/downloadFile/lmood9-2.jpg><br/>", "/file/downloadFile/lmood9-thumbnail.jpg", 50, Product.Category.TOP);

            Product lkmostP1 = saveProduct(lkmost, "트레이닝 와이드 팬츠", 150, "<p>라이크더모스트 트레이닝 핀턱 와이드 팬츠</p><br/> <img src=/file/downloadFile/lkmost1-1.jpg><br/> <img src=/file/downloadFile/lkmost1-2.jpg><br/> <img src=/file/downloadFile/lkmost1-3.jpg><br/> <img src=/file/downloadFile/lkmost1-4.jpg><br/>", "/file/downloadFile/lkmost1-thumbnail.jpg", 100, Product.Category.BOTTOM);
            Product lkmostP2 = saveProduct(lkmost, "트레이닝 사이드핀턱 와이드 팬츠", 150, "<p>라이크더모스트 트레이닝 사이드핀턱 와이드 팬츠</p><br/> <img src=/file/downloadFile/lkmost2-1.jpg><br/> <img src=/file/downloadFile/lkmost2-2.jpg><br/> <img src=/file/downloadFile/lkmost2-3.jpg><br/>", "/file/downloadFile/lkmost2-thumbnail.jpg", 100, Product.Category.BOTTOM);
            Product lkmostP3 = saveProduct(lkmost, "우먼 소프트 스트링 와이드 팬츠", 150, "<p>라이크더모스트 우먼 소프트 스트링 트레이닝 와이드 팬츠</p><br/> <img src=/file/downloadFile/lkmost3-1.jpg><br/> <img src=/file/downloadFile/lkmost3-2.jpg><br/> <img src=/file/downloadFile/lkmost3-3.jpg><br/>", "/file/downloadFile/lkmost3-thumbnail.jpg", 100, Product.Category.BOTTOM);
            Product lkmostP4 = saveProduct(lkmost, "우먼 크롭 집헙후드", 250, "<p>라이크더모스트 우먼 소프트 투웨이 자수 크롭 집업후드</p><br/> <img src=/file/downloadFile/lkmost4-1.jpg><br/> <img src=/file/downloadFile/lkmost4-2.jpg><br/> <img src=/file/downloadFile/lkmost4-3.jpg><br/>", "/file/downloadFile/lkmost4-thumbnail.jpg", 50, Product.Category.OUTER);
            Product lkmostP5 = saveProduct(lkmost, "무지 기모 후드", 250, "<p>라이크더모스트 투웨이 집업 무지 기모 후드</p><br/> <img src=/file/downloadFile/lkmost5-1.jpg><br/> <img src=/file/downloadFile/lkmost5-2.jpg><br/> <img src=/file/downloadFile/lkmost5-3.jpg><br/>", "/file/downloadFile/lkmost5-thumbnail.jpg", 100, Product.Category.OUTER);
            Product lkmostP6 = saveProduct(lkmost, "우먼 레귤러 티셔츠", 100, "<p>라이크더모스트 원캣 우먼 레귤러 베이직 티셔츠</p><br/> <img src=/file/downloadFile/lkmost6-1.jpg><br/> <img src=/file/downloadFile/lkmost6-2.jpg><br/> <img src=/file/downloadFile/lkmost6-3.jpg><br/>", "/file/downloadFile/lkmost6-thumbnail.jpg", 50, Product.Category.TOP);

            Product eight1 = saveProduct(eightseconds, "에센셜 데님 팬츠", 200, "<p>8 seconds 에센셜 테이퍼드핏 데님 팬츠</p><br/> <img src=/file/downloadFile/8seconds1-1.jpg><br/> <img src=/file/downloadFile/8seconds1-2.jpg><br/> <img src=/file/downloadFile/8seconds1-3.jpg><br/>", "/file/downloadFile/8seconds1-thumbnail.jpg", 50, Product.Category.BOTTOM);
            Product eight2 = saveProduct(eightseconds, "코튼 스팟 팬츠", 200, "<p>8 seconds 코튼 스팟 와이드핏 팬츠</p><br/> <img src=/file/downloadFile/8seconds2-1.jpg><br/> <img src=/file/downloadFile/8seconds2-2.jpg><br/> <img src=/file/downloadFile/8seconds2-3.jpg><br/>", "/file/downloadFile/8seconds2-thumbnail.jpg", 50, Product.Category.BOTTOM);
            Product eight3 = saveProduct(eightseconds, "부클 니트", 200, "<p>8 seconds 부클 라운드넥 니트</p><br/> <img src=/file/downloadFile/8seconds3-1.jpg><br/> <img src=/file/downloadFile/8seconds3-2.jpg><br/> <img src=/file/downloadFile/8seconds3-3.jpg><br/>", "/file/downloadFile/8seconds3-thumbnail.jpg", 50, Product.Category.TOP);
            Product eight4 = saveProduct(eightseconds, "후드 숏 재킷", 500, "<p>8 seconds 후드 숏 다운 재킷</p><br/> <img src=/file/downloadFile/8seconds4-1.jpg><br/> <img src=/file/downloadFile/8seconds4-2.jpg><br/> <img src=/file/downloadFile/8seconds4-3.jpg><br/>", "/file/downloadFile/8seconds4-thumbnail.jpg", 50, Product.Category.OUTER);
            Product eight5 = saveProduct(eightseconds, "와이드 데님팬츠", 200, "<p>8 seconds 밑단여밈 와이드 데님팬츠</p><br/> <img src=/file/downloadFile/8seconds5-1.jpg><br/> <img src=/file/downloadFile/8seconds5-2.jpg><br/> <img src=/file/downloadFile/8seconds5-3.jpg><br/>", "/file/downloadFile/8seconds5-thumbnail.jpg", 50, Product.Category.BOTTOM);
            Product eight6 = saveProduct(eightseconds, "데님 롱스커트 블랙", 200, "<p>8 seconds 투턱 데님 롱스커트 블랙</p><br/> <img src=/file/downloadFile/8seconds6-1.jpg><br/> <img src=/file/downloadFile/8seconds6-2.jpg><br/> <img src=/file/downloadFile/8seconds6-3.jpg><br/>", "/file/downloadFile/8seconds6-thumbnail.jpg", 50, Product.Category.BOTTOM);
            Product eight7 = saveProduct(eightseconds, "라운드넥 풀오버", 200, "<p>8 seconds 볼륨 소매 라운드넥 풀오버</p><br/> <img src=/file/downloadFile/8seconds7-1.jpg><br/> <img src=/file/downloadFile/8seconds7-2.jpg><br/> <img src=/file/downloadFile/8seconds7-3.jpg><br/> <img src=/file/downloadFile/8seconds7-4.jpg><br/>", "/file/downloadFile/8seconds7-thumbnail.jpg", 50, Product.Category.TOP);
            Product eight8 = saveProduct(eightseconds, "캐시미어 머플러", 150, "<p>8 seconds 캐시미어 블렌디드 솔리드 머플러</p><br/> <img src=/file/downloadFile/8seconds8-1.jpg><br/> <img src=/file/downloadFile/8seconds8-2.jpg><br/> <img src=/file/downloadFile/8seconds8-3.jpg><br/>", "/file/downloadFile/8seconds8-thumbnail.jpg", 50, Product.Category.ACCESSORY);
            Product eight9 = saveProduct(eightseconds, "크랙 호보백", 250, "<p>8 seconds 크랙 캐주얼 호보백</p><br/> <img src=/file/downloadFile/8seconds9-1.jpg><br/> <img src=/file/downloadFile/8seconds9-2.jpg><br/> <img src=/file/downloadFile/8seconds9-3.jpg><br/>", "/file/downloadFile/8seconds9-thumbnail.jpg", 50, Product.Category.ACCESSORY);
            Product eight10 = saveProduct(eightseconds, "원버튼 숏코트 브라운", 470, "<p>8 seconds 스탠드 칼라 원버튼 핸드메이드 숏코트 브라운</p><br/> <img src=/file/downloadFile/8seconds10-1.jpg><br/> <img src=/file/downloadFile/8seconds10-2.jpg><br/> <img src=/file/downloadFile/8seconds10-3.jpg><br/> <img src=/file/downloadFile/8seconds10-4.jpg><br/>", "/file/downloadFile/8seconds10-thumbnail.jpg", 50, Product.Category.OUTER);
            Product eight11 = saveProduct(eightseconds, "코듀로이 숏 패딩 아이보리", 500, "<p>8 seconds 왕골 코듀로이 숏 패딩 아이보리</p><br/> <img src=/file/downloadFile/8seconds11-1.jpg><br/> <img src=/file/downloadFile/8seconds11-2.jpg><br/> <img src=/file/downloadFile/8seconds11-3.jpg><br/> <img src=/file/downloadFile/8seconds11-4.jpg><br/>", "/file/downloadFile/8seconds11-thumbnail.jpg", 50, Product.Category.OUTER);

            addCart(userA, lmoodP1, 10);
            addCart(userA, lmoodP2, 10);

            Orders orders = saveOrder(userA, lmoodP1, 10);

            saveReview(orders, userA);
            Favorite userAFavorite1 = addFavorite(userA, lmoodP1);
            Favorite userAFavorite2 = addFavorite(userA, lmoodP2);
            Favorite userAFavorite3 = addFavorite(userA, lmoodP3);
        }

        private Favorite addFavorite(User user, Product product) {
            Favorite favorite = Favorite.create(user, product);

            return favoriteRepository.save(favorite);
        }

        private User saveUser(String username, String password, String name, String email) {
            User user = User.create(username, passwordEncoder.encode(password), name, email);

            return userRepository.save(user);
        }

        private void grantRoleUser(User user) {
            UserRole userRole = UserRole.create(user, ROLE_USER.name());
            userRoleRepository.save(userRole);
        }

        private Product saveProduct(User userId, String name, Integer price, String description, String thumbnailUrl, Integer stockQuantity, Product.Category category) {
            Product product = Product.create(userId, name, price, description, thumbnailUrl, stockQuantity, category.name());

            return productRepository.save(product);
        }

        private Cart addCart(User user, Product product, Integer quantity) {
            Cart cart = Cart.create(user, product, quantity);

            return cartRepository.save(cart);
        }

        private Orders saveOrder(User user, Product product, Integer quantity) {
            Payment payment = Payment.of(user, "testPayment", "PAID", product.getPrice() * quantity, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(LocalDateTime.now()), null, null,
                    "korea seoul");
            Payment savePayment = paymentRepository.save(payment);

            Orders orders = Orders.create(user, savePayment);

            List<OrderProduct> orderProductList = new ArrayList<>();
            OrderProduct orderProduct = OrderProduct.create(orders, product, user, quantity);
            orderProductList.add(orderProduct);

            Orders saveOrders = ordersRepository.save(orders);
            List<OrderProduct> saveOrderProductList = orderProductRepository.saveAll(orderProductList);

            return saveOrders;
        }

        private void saveReview(Orders orders, User userA) {
            List<OrderProduct> orderProductList = orderProductRepository.findAllByOrdersId(orders.getId());
            for (OrderProduct orderProduct : orderProductList) {
                Review review = Review.create(userA, orderProduct, "testReview", 5);
                Review saveReview = reviewRepository.save(review);
            }
        }
    }
}
