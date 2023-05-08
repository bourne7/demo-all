package com.pbr.dao.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author niekang
 */
@Data
@Document("ks_bin_space_update_record")
public class KsBinSpaceUpdateRecord {

    @Field(value = "ksBinId")
    private Long ksBinId;

    @Field(value = "ksBinCode")
    private String ksBinCode;

    @Field(value = "ksSpaceId")
    private Long ksSpaceId;

    @Field(value = "ksSpaceCode")
    private String ksSpaceCode;

    @Field(value = "ksBinStatus")
    private int ksBinStatus;

    @Field(value = "stationId")
    private Long stationId;

    @Field(value = "create_time")
    @CreatedDate
    @Indexed
    private Date createTime;

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        int currentHour = localDateTime.getHour();
        int utcHour = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().getHour();
        System.out.println(currentHour - utcHour);
    }

    /**
     * //TODO
     * mongo use UTC time ,so when we must add 8 hours, but if the project deployed in other country ,the time is wrong.The best way is set the mongo time zone
     *
     * @return
     */
    public Date getCreateTime() {
//        if (createTime == null) {
//            return null;
//        }
//
//        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
//        int currentHour = localDateTime.getHour();
//        int utcHour = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().getHour();
//        return DateUtils.addHours(createTime, currentHour - utcHour);

        return createTime;
    }
}
