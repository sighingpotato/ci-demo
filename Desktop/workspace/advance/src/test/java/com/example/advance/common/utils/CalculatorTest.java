package com.example.advance.common.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.out.println("BeforeEach 실행");
    }

    @AfterEach
    void clear() {
        System.out.println("각각의 테스트 끝");
    }

    @Test
    void add() {

        // given
        int num1 = 2;
        int num2 = 3;

        // when
        int result = calculator.add(num1, num2);

        // then
        assertEquals(5, result);
    }

    @Test
    void subtract() {

        System.out.println("subtract 메서드 실행");

        int result = calculator.subtract(5, 3);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("나누기 성공 케이스 테스트")
    void divid_success() {

        int result = calculator.divide(10, 2);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("나누기 실패 케이스 테스트 - 0으로 숫자를 나눌 때")
    void divide_fail() {

        // given
        int num1 = 10;
        int num2 = 0;

        // when & then
        assertThrows(ArithmeticException.class,
                () -> calculator.divide(num1, num2));

        // 0을 나눌 것
        // ArithmeticException 에러가 발생할 것

/*        System.out.println("나누기 실패 케이스 테스트");

        assertThrows(ArithmeticException.class,
                () -> calculator.divide(10, 0));*/

/*        int result = calculator.divide(10, 0);

        assertEquals(5, result);*/
    }

}