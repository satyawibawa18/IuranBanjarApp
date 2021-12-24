package id.fabian.pembayaraniuranbanjar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface wargaDao {
    @Query("SELECT * FROM wargaEntity")
    List<wargaEntity> getAll();

    @Query("INSERT INTO wargaEntity (Nama,Alamat,NIK,JenisKelamin,Iuran,AnggotaKK) VALUES(:Nama,:Alamat,:NIK,:JenisKelamin,:Iuran,:AnggotaKK)")
    void insertAll(String Nama, String Alamat, Integer NIK , String JenisKelamin, String Iuran, Integer AnggotaKK);

    //edit
    @Query("UPDATE wargaEntity SET Nama=:Nama, Alamat=:Alamat, NIK=:NIK, JenisKelamin=:JenisKelamin WHERE idWarga=:idWarga")
    void update(int idWarga, String Nama, String Alamat, Integer NIK , String JenisKelamin);

    @Query("SELECT * FROM wargaEntity WHERE idWarga=:idWarga")
    wargaEntity get(int idWarga);

    @Delete
    void delete(wargaEntity wargaEntity);

}
