package com.eruption.eruption.goods.service;


import com.eruption.eruption.global.exception.DuplicateEmailException;
import com.eruption.eruption.global.exception.GoodsNotFoundException;
import com.eruption.eruption.goods.dto.GoodsDetailResponse;
import com.eruption.eruption.goods.entity.Goods;
import com.eruption.eruption.goods.entity.GoodsOption;
import com.eruption.eruption.goods.repository.GoodsOptionRepository;
import com.eruption.eruption.goods.repository.GoodsRepository;
import com.eruption.eruption.user.dto.SignUpRequest;
import com.eruption.eruption.user.dto.SignUpResponse;
import com.eruption.eruption.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GoodsServiceTest {

    @Mock
    GoodsRepository goodsRepository;
    @InjectMocks
    GoodsService goodsService;
    @Mock
    GoodsOptionRepository goodsOptionRepository;


    @Test
    void 굿즈_상세조회시_굿즈정보와_옵션목록이_함께_반환된다() {
        // given
        Goods goods = Goods.builder()
                .title("테스트 굿즈")
                .description("설명")
                .reservationStartAt(LocalDateTime.now())
                .reservationEndAt(LocalDateTime.now().plusDays(7))
                .build();

        GoodsOption option1 = GoodsOption.builder()
                .goods(goods).optionName("기본 옵션").price(15000).stockQty(10).maxQty(2).build();
        GoodsOption option2 = GoodsOption.builder()
                .goods(goods).optionName("한정 옵션").price(25000).stockQty(1).maxQty(1).build();

        given(goodsRepository.findById(1L)).willReturn(Optional.of(goods));
        given(goodsOptionRepository.findByGoodsId(1L)).willReturn(List.of(option1, option2));

        // when
        GoodsDetailResponse response = goodsService.getGoodsDetail(1L);

        // then
        assertThat(response.title()).isEqualTo("테스트 굿즈");
        assertThat(response.options()).hasSize(2);
        assertThat(response.options().get(0).optionName()).isEqualTo("기본 옵션");
    }
    @Test
    void 없는_굿즈를_조회하면_예외가_발생한다(){
        // given
        given(goodsRepository.findById(999L)).willReturn(Optional.empty());

        // when 없는 굿즈 조회
        assertThatThrownBy(() ->
                goodsService.getGoodsDetail(999L))
                .isInstanceOf(GoodsNotFoundException.class);
        // then
    }

}
