package dev.trindadedev.theblocklogicsjava.beans;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DEPRECATION")
public class BlockBean implements Parcelable {

  public static final Creator<BlockBean> CREATOR =
      new Creator<>() {
        @Override
        public BlockBean createFromParcel(Parcel parcel) {
          return new BlockBean(parcel);
        }

        @Override
        public BlockBean[] newArray(int size) {
          return new BlockBean[size];
        }
      };

  public int type;
  public int color;
  public String id;
  public List<Param<?>> params;

  public BlockBean() {
    params = new ArrayList<>();
  }

  public BlockBean(final Parcel parcel) {
    type = parcel.readInt();
    color = parcel.readInt();
    id = parcel.readString();
    params = new ArrayList<>();
    parcel.readList(params, Param.class.getClassLoader());
  }

  @Override
  public void writeToParcel(final Parcel parcel, final int arg1) {
    parcel.writeInt(type);
    parcel.writeInt(color);
    parcel.writeString(id);
    parcel.writeList(params);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static class Param<T> {
    public String name;
    public Class<T> typeClass;
    public T defValue;

    public Param(final String name, final Class<T> typeClass, final T value) {
      this.name = name;
      this.typeClass = typeClass;
      this.defValue = defValue;
    }
  }
}
