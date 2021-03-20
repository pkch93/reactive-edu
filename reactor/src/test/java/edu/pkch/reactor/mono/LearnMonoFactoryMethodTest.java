package edu.pkch.reactor.mono;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LearnMonoFactoryMethodTest {

    @Test
    @DisplayName("just는 인자를 받아서 Mono로 감싼다")
    void just() throws InterruptedException {
        // given & when
        Mono<Integer> actual = Mono.just(1)
                .subscribeOn(Schedulers.single());

        // then
        actual.subscribe(number -> assertThat(number).isEqualTo(1));

        Thread.sleep(1000);
    }

    @Test
    @DisplayName("just의 인자가 null이면 NullPointerException 예외를 던진다.")
    void just_when_input_is_null_then_throw_NullPointerException() {
        // given & when & then
        assertThatThrownBy(() -> Mono.just(null))
                .isInstanceOf(NullPointerException.class);
    }
}
