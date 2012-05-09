/**
 * This class is automatically generated by mig. DO NOT EDIT THIS FILE.
 * This class implements a Java interface to the 'SerialMsg'
 * message type.
 */

public class SerialMsg extends net.tinyos.message.Message {

    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 11;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = 6;

    /** Create a new SerialMsg of size 11. */
    public SerialMsg() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new SerialMsg of the given data_length. */
    public SerialMsg(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new SerialMsg with the given data_length
     * and base offset.
     */
    public SerialMsg(int data_length, int base_offset) {
        super(data_length, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new SerialMsg using the given byte array
     * as backing store.
     */
    public SerialMsg(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new SerialMsg using the given byte array
     * as backing store, with the given base offset.
     */
    public SerialMsg(byte[] data, int base_offset) {
        super(data, base_offset);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new SerialMsg using the given byte array
     * as backing store, with the given base offset and data length.
     */
    public SerialMsg(byte[] data, int base_offset, int data_length) {
        super(data, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new SerialMsg embedded in the given message
     * at the given base offset.
     */
    public SerialMsg(net.tinyos.message.Message msg, int base_offset) {
        super(msg, base_offset, DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new SerialMsg embedded in the given message
     * at the given base offset and length.
     */
    public SerialMsg(net.tinyos.message.Message msg, int base_offset, int data_length) {
        super(msg, base_offset, data_length);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <SerialMsg> \n";
      try {
        s += "  [seqNum=0x"+Long.toHexString(get_seqNum())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [ledNum=0x"+Long.toHexString(get_ledNum())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [sender=0x"+Long.toHexString(get_sender())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [receiver=0x"+Long.toHexString(get_receiver())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [sensor=";
        for (int i = 0; i < 2; i++) {
          s += "0x"+Long.toHexString(getElement_sensor(i) & 0xff)+" ";
        }
        s += "]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      try {
        s += "  [isAck=0x"+Long.toHexString(get_isAck())+"]\n";
      } catch (ArrayIndexOutOfBoundsException aioobe) { /* Skip field */ }
      return s;
    }

    // Message-type-specific access methods appear below.

    /////////////////////////////////////////////////////////
    // Accessor methods for field: seqNum
    //   Field type: int, unsigned
    //   Offset (bits): 0
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'seqNum' is signed (false).
     */
    public static boolean isSigned_seqNum() {
        return false;
    }

    /**
     * Return whether the field 'seqNum' is an array (false).
     */
    public static boolean isArray_seqNum() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'seqNum'
     */
    public static int offset_seqNum() {
        return (0 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'seqNum'
     */
    public static int offsetBits_seqNum() {
        return 0;
    }

    /**
     * Return the value (as a int) of the field 'seqNum'
     */
    public int get_seqNum() {
        return (int)getUIntBEElement(offsetBits_seqNum(), 16);
    }

    /**
     * Set the value of the field 'seqNum'
     */
    public void set_seqNum(int value) {
        setUIntBEElement(offsetBits_seqNum(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'seqNum'
     */
    public static int size_seqNum() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'seqNum'
     */
    public static int sizeBits_seqNum() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: ledNum
    //   Field type: int, unsigned
    //   Offset (bits): 16
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'ledNum' is signed (false).
     */
    public static boolean isSigned_ledNum() {
        return false;
    }

    /**
     * Return whether the field 'ledNum' is an array (false).
     */
    public static boolean isArray_ledNum() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'ledNum'
     */
    public static int offset_ledNum() {
        return (16 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'ledNum'
     */
    public static int offsetBits_ledNum() {
        return 16;
    }

    /**
     * Return the value (as a int) of the field 'ledNum'
     */
    public int get_ledNum() {
        return (int)getUIntBEElement(offsetBits_ledNum(), 16);
    }

    /**
     * Set the value of the field 'ledNum'
     */
    public void set_ledNum(int value) {
        setUIntBEElement(offsetBits_ledNum(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'ledNum'
     */
    public static int size_ledNum() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'ledNum'
     */
    public static int sizeBits_ledNum() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: sender
    //   Field type: int, unsigned
    //   Offset (bits): 32
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'sender' is signed (false).
     */
    public static boolean isSigned_sender() {
        return false;
    }

    /**
     * Return whether the field 'sender' is an array (false).
     */
    public static boolean isArray_sender() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'sender'
     */
    public static int offset_sender() {
        return (32 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'sender'
     */
    public static int offsetBits_sender() {
        return 32;
    }

    /**
     * Return the value (as a int) of the field 'sender'
     */
    public int get_sender() {
        return (int)getUIntBEElement(offsetBits_sender(), 16);
    }

    /**
     * Set the value of the field 'sender'
     */
    public void set_sender(int value) {
        setUIntBEElement(offsetBits_sender(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'sender'
     */
    public static int size_sender() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'sender'
     */
    public static int sizeBits_sender() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: receiver
    //   Field type: int, unsigned
    //   Offset (bits): 48
    //   Size (bits): 16
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'receiver' is signed (false).
     */
    public static boolean isSigned_receiver() {
        return false;
    }

    /**
     * Return whether the field 'receiver' is an array (false).
     */
    public static boolean isArray_receiver() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'receiver'
     */
    public static int offset_receiver() {
        return (48 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'receiver'
     */
    public static int offsetBits_receiver() {
        return 48;
    }

    /**
     * Return the value (as a int) of the field 'receiver'
     */
    public int get_receiver() {
        return (int)getUIntBEElement(offsetBits_receiver(), 16);
    }

    /**
     * Set the value of the field 'receiver'
     */
    public void set_receiver(int value) {
        setUIntBEElement(offsetBits_receiver(), 16, value);
    }

    /**
     * Return the size, in bytes, of the field 'receiver'
     */
    public static int size_receiver() {
        return (16 / 8);
    }

    /**
     * Return the size, in bits, of the field 'receiver'
     */
    public static int sizeBits_receiver() {
        return 16;
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: sensor
    //   Field type: short[], unsigned
    //   Offset (bits): 64
    //   Size of each element (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'sensor' is signed (false).
     */
    public static boolean isSigned_sensor() {
        return false;
    }

    /**
     * Return whether the field 'sensor' is an array (true).
     */
    public static boolean isArray_sensor() {
        return true;
    }

    /**
     * Return the offset (in bytes) of the field 'sensor'
     */
    public static int offset_sensor(int index1) {
        int offset = 64;
        if (index1 < 0 || index1 >= 2) throw new ArrayIndexOutOfBoundsException();
        offset += 0 + index1 * 8;
        return (offset / 8);
    }

    /**
     * Return the offset (in bits) of the field 'sensor'
     */
    public static int offsetBits_sensor(int index1) {
        int offset = 64;
        if (index1 < 0 || index1 >= 2) throw new ArrayIndexOutOfBoundsException();
        offset += 0 + index1 * 8;
        return offset;
    }

    /**
     * Return the entire array 'sensor' as a short[]
     */
    public short[] get_sensor() {
        short[] tmp = new short[2];
        for (int index0 = 0; index0 < numElements_sensor(0); index0++) {
            tmp[index0] = getElement_sensor(index0);
        }
        return tmp;
    }

    /**
     * Set the contents of the array 'sensor' from the given short[]
     */
    public void set_sensor(short[] value) {
        for (int index0 = 0; index0 < value.length; index0++) {
            setElement_sensor(index0, value[index0]);
        }
    }

    /**
     * Return an element (as a short) of the array 'sensor'
     */
    public short getElement_sensor(int index1) {
        return (short)getUIntBEElement(offsetBits_sensor(index1), 8);
    }

    /**
     * Set an element of the array 'sensor'
     */
    public void setElement_sensor(int index1, short value) {
        setUIntBEElement(offsetBits_sensor(index1), 8, value);
    }

    /**
     * Return the total size, in bytes, of the array 'sensor'
     */
    public static int totalSize_sensor() {
        return (16 / 8);
    }

    /**
     * Return the total size, in bits, of the array 'sensor'
     */
    public static int totalSizeBits_sensor() {
        return 16;
    }

    /**
     * Return the size, in bytes, of each element of the array 'sensor'
     */
    public static int elementSize_sensor() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of each element of the array 'sensor'
     */
    public static int elementSizeBits_sensor() {
        return 8;
    }

    /**
     * Return the number of dimensions in the array 'sensor'
     */
    public static int numDimensions_sensor() {
        return 1;
    }

    /**
     * Return the number of elements in the array 'sensor'
     */
    public static int numElements_sensor() {
        return 2;
    }

    /**
     * Return the number of elements in the array 'sensor'
     * for the given dimension.
     */
    public static int numElements_sensor(int dimension) {
      int array_dims[] = { 2,  };
        if (dimension < 0 || dimension >= 1) throw new ArrayIndexOutOfBoundsException();
        if (array_dims[dimension] == 0) throw new IllegalArgumentException("Array dimension "+dimension+" has unknown size");
        return array_dims[dimension];
    }

    /**
     * Fill in the array 'sensor' with a String
     */
    public void setString_sensor(String s) { 
         int len = s.length();
         int i;
         for (i = 0; i < len; i++) {
             setElement_sensor(i, (short)s.charAt(i));
         }
         setElement_sensor(i, (short)0); //null terminate
    }

    /**
     * Read the array 'sensor' as a String
     */
    public String getString_sensor() { 
         char carr[] = new char[Math.min(net.tinyos.message.Message.MAX_CONVERTED_STRING_LENGTH,2)];
         int i;
         for (i = 0; i < carr.length; i++) {
             if ((char)getElement_sensor(i) == (char)0) break;
             carr[i] = (char)getElement_sensor(i);
         }
         return new String(carr,0,i);
    }

    /////////////////////////////////////////////////////////
    // Accessor methods for field: isAck
    //   Field type: short, unsigned
    //   Offset (bits): 80
    //   Size (bits): 8
    /////////////////////////////////////////////////////////

    /**
     * Return whether the field 'isAck' is signed (false).
     */
    public static boolean isSigned_isAck() {
        return false;
    }

    /**
     * Return whether the field 'isAck' is an array (false).
     */
    public static boolean isArray_isAck() {
        return false;
    }

    /**
     * Return the offset (in bytes) of the field 'isAck'
     */
    public static int offset_isAck() {
        return (80 / 8);
    }

    /**
     * Return the offset (in bits) of the field 'isAck'
     */
    public static int offsetBits_isAck() {
        return 80;
    }

    /**
     * Return the value (as a short) of the field 'isAck'
     */
    public short get_isAck() {
        return (short)getUIntBEElement(offsetBits_isAck(), 8);
    }

    /**
     * Set the value of the field 'isAck'
     */
    public void set_isAck(short value) {
        setUIntBEElement(offsetBits_isAck(), 8, value);
    }

    /**
     * Return the size, in bytes, of the field 'isAck'
     */
    public static int size_isAck() {
        return (8 / 8);
    }

    /**
     * Return the size, in bits, of the field 'isAck'
     */
    public static int sizeBits_isAck() {
        return 8;
    }

}
