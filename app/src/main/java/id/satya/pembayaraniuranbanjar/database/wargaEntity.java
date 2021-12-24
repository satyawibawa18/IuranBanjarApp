package id.fabian.pembayaraniuranbanjar.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class wargaEntity {
    @PrimaryKey
    public int idWarga;

    @ColumnInfo(name = "Nama")
    public String fullname;

    @ColumnInfo(name = "Alamat")
    public String alamat;

    @ColumnInfo(name = "NIK")
    public int NIK_KITAS;

    @ColumnInfo(name = "JenisKelamin")
    public String jeniskelamin;

    @ColumnInfo(name = "Iuran")
    public String iuran;

    @ColumnInfo(name = "AnggotaKK")
    public int anggota;

}
