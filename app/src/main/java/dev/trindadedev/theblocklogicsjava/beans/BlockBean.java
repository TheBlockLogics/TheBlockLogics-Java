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
  public int subStack1;
  public int subStack2;
  public String id;
  public String spec;
  public String opCode;
  public ArrayList<String> parameters;
  public ArrayList<String> parametersTypes;

  public BlockBean() {
    parameters = new ArrayList<>();
    parametersTypes = new ArrayList<>();
  }

  public BlockBean(final Parcel parcel) {
    type = parcel.readInt();
    color = parcel.readInt();
    id = parcel.readString();
    parameters = (ArrayList) parcel.readSerializable();
    parametersTypes = (ArrayList) parcel.readSerializable();
  }

  @Override
  public void writeToParcel(final Parcel parcel, final int arg1) {
    parcel.writeInt(type);
    parcel.writeInt(color);
    parcel.writeString(id);
    parcel.writeSerializable(parameters);
    parcel.writeSerializable(parametersTypes);
  }

  @Override
  public int describeContents() {
    return 0;
  }
}
