package br.ce.wcaquino.taskbackend.utils;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;

public class DateUtilsTest {
    @Test 
    public void deveRetornarTrueParaDatasFuturas() {
        LocalDate date = LocalDate.of(2026, 1, 1);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void deveRetornarFalseParaDatasPassadas() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void deveRetornarTrueParaDatasAtuais() {
        LocalDate date = LocalDate.now();
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }
} 