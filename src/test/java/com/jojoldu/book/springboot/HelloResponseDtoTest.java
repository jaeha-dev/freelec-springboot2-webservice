package com.jojoldu.book.springboot;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @title  : 실습용 Response Dto 테스트 클래스
 * @author : jaeha-dev (Git)
 */
public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // Given
        String name = "테스트";
        int amount = 100;

        // When (생성자)
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // Then (Getter)
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}