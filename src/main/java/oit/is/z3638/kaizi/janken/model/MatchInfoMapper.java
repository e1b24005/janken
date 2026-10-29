package oit.is.z3638.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
  @Select("SELECT id,user1,user2,user1Hand,isActive from matchinfo where id = #{id}")
  MatchInfo selectById(int id);

  /**
   * @param match
   */
  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchinfo);


  @Select("SELECT * FROM matchinfo;")
  ArrayList<MatchInfo> selectAllMatchInfo();

  @Select("SELECT * FROM matchinfo WHERE (user1 = #{userid} OR user2 = #{userid}) AND isActive = TRUE")
  MatchInfo selectActiveMatchInfo(int userid);

  @Update("UPDATE matchinfo SET isActive = FALSE WHERE id = #{id}")
void updateIsActiveFalse(int id);
}
