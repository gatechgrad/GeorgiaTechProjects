## DEVICES_T - a table containing all of the devices on the system
CREATE TABLE DEVICES_T (
  DEVICE_ID INTEGER,
  DEVICE_TYPE CHAR(50),
  NAME CHAR(50),
  LOCATION CHAR(50),
  DESCRIPTION CHAR(50),

  CONNECTED_TO INTEGER

);

## SENSORS_T - an abstract table holding data for all sensor devices
CREATE TABLE SENSORS_T (
  STATUS CHAR(50)
);

## METERS_T - an abstract table holding data for all meter devices
CREATE TABLE METERS_T (
  STATUS CHAR(50)
);

## PUMPS_T - an abstract table holding data for all pump devices
CREATE TABLE PUMPS_T (

## valid STATUS values are "On", "Standby", and "Off"
  STATUS CHAR(50),

## wether or not the alarm state is on
##  ALARM_ON LOGICAL
);

## VALVES_T - table containing data for all valves
CREATE TABLE VALVES_T (
## valid STATUS values are "Open" and "Closed"
  STATUS CHAR(50)
);

## HEAT_PUMPS_T - table containing data specific for heat pumps
CREATE TABLE HEAT_PUMPS_T (
  STATUS CHAR(50)
);

## WATER_PUMPS_T - table containing data specific for water pumps
CREATE TABLE WATER_PUMPS_T (
  STATUS CHAR(50)
);

## AIR_TEMPERATURE_SENSORS_T - table containing data specific for temperature sensors
CREATE TABLE AIR_TEMPERATURE_SENSORS_T (
  STATUS CHAR(50)
);

## FLOW_METERS_T - table containing data specific for flow meters
CREATE TABLE FLOW_METERS_T (
  STATUS CHAR(50)
);


## USERS_T - table containing information on all users
CREATE TABLE USERS_T (
  USER_ID CHAR(50),
  USER_PASSWORD CHAR(50),
  USER_TYPE INTEGER
);

## MAINTAINERS_T - table containing information specific for maintaining users
CREATE TABLE MAINTAINERS_T (

);

## SUPER_USERS_T - table containing information specific for super users
CREATE TABLE SUPER_USERS_T (

);


## MAINTENANCE_T - table containing maintenance data for a device
CREATE TABLE MAINTENANCE_T (
  MAINTENANCE_TYPE INTEGER,
  ENGINEER CHAR(50),
  DESCRIPTION CHAR(50)
); 


## SYSTEM_T - a table with a set of devices
CREATE TABLE SYSTEM_T (

## STATUS holds the state of the systems; either red, yellow, green, or blue
  STATUS CHAR(50)

);

