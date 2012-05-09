#ifndef TEST_SERIAL_H
#define TEST_SERIAL_H

enum {
  AM_COMMANDMSG 			= 6,
  AM_BEACONMSG 				= 5,
  AM_SENSORMSG				= 4,
  AM_TABLEMSG				= 3,
  AM_SENDPERIOD 			= 1000,
  AM_BEACONINTERVAL 		= 2000,
  AM_BEACONTIMEOUT			= 15, // in seconds
  AM_TABLESIZE 				= 4,
  AM_SENDRADIOQ_LEN			= 2,
  AM_ACKTIMEOUT				= 2000,
  AM_MAXNODEID				= 65535,
  NREADINGS 				= 10, // count of samples
  DEFAULT_SAMPLING_INTERVAL = 256 // Default sampling period.
};

typedef nx_struct CommandMsg {
  nx_uint16_t seqNum;
  nx_uint16_t ledNum;
  nx_uint16_t sender;
  nx_uint16_t receiver;
  nx_uint8_t sensor[2]; //starts or stops reading of 3 sensors
  nx_uint8_t isAck;
} CommandMsg;

typedef nx_struct BeaconMsg {
  nx_uint16_t sender;
} BeaconMsg;

typedef struct MoteTableEntry {
  uint16_t nodeId;
  bool ackReceived;
  uint16_t lastContact;
  bool expired;
} MoteTableEntry;

typedef nx_struct SensorMsg {
  nx_uint16_t version; /* Version of the interval. */
  nx_uint8_t sensor; /* From which sensor? 0 means not active*/
  nx_uint16_t interval; /* Samping period. */
  nx_uint16_t id; /* Mote id of sending mote. */
  nx_uint16_t count; /* The readings are samples count * NREADINGS onwards */
  nx_uint16_t readings[NREADINGS];
} SensorMsg;

typedef nx_struct TableMsg {
  nx_uint16_t sender; /* Version of the interval. */
  nx_uint8_t receiver; /* From which sensor? */
  nx_uint16_t nodeId[AM_TABLESIZE];
  nx_uint16_t lastContact[AM_TABLESIZE]; 
} TableMsg;



#endif
