package com.kuponburada.KuponBurada.config;

import com.kuponburada.KuponBurada.dto.request.CouponRequest;
import com.kuponburada.KuponBurada.entity.Coupon;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // String -> ZonedDateTime converter
        modelMapper.createTypeMap(String.class, ZonedDateTime.class).setConverter(

                context -> {
                    String source = context.getSource();
                    if (source == null || source.isEmpty()) {
                        return null;
                    }
                    // "yyyy-MM-dd'T'HH:mm:ss" formatında string bekleniyor
                    return ZonedDateTime.of(LocalDateTime.parse(source), ZoneId.systemDefault());

                }
        );

        // String -> DiscountType converter
        modelMapper.createTypeMap(String.class, Coupon.DiscountType.class).setConverter(
                context -> {
                    String source = context.getSource();
                    if (source == null || source.isEmpty()) {
                        return null;
                    }
                    return Coupon.DiscountType.valueOf(source.toUpperCase());
                }
        );

        modelMapper.createTypeMap(CouponRequest.class, Coupon.class)
                .addMappings(mapper -> {
                    mapper.skip(Coupon::setId);
                    mapper.using(ctx -> {
                        String src = ((String) ctx.getSource());
                        if (src == null || src.isEmpty()) {
                            return null;
                        }
                        return Coupon.DiscountType.valueOf(src.toUpperCase());
                    }).map(CouponRequest::getDiscountType, Coupon::setDiscountType);

                    mapper.using(ctx -> {
                        String src = ((String) ctx.getSource());
                        if (src == null || src.isEmpty()) {
                            return null;
                        }
                        return ZonedDateTime.of(LocalDateTime.parse(src), ZoneId.systemDefault());
                    }).map(CouponRequest::getStartDate, Coupon::setStartDate);

                    mapper.using(ctx -> {
                        String src = ((String) ctx.getSource());
                        if (src == null || src.isEmpty()) {
                            return null;
                        }
                        return ZonedDateTime.of(LocalDateTime.parse(src), ZoneId.systemDefault());
                    }).map(CouponRequest::getEndDate, Coupon::setEndDate);
                });

        return modelMapper;
    }
}
