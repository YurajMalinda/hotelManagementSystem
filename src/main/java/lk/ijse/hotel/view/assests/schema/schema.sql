drop database hotelManagementSystem;

create database hotelManagementSystem;

use hotelManagementSystem;

create table userDTO(
                     userId VARCHAR(6) NOT NULL,
                     userName VARCHAR(30) NOT NULL,
                     password VARCHAR(10) NOT NULL,
                     title VARCHAR(30) NOT NULL,
                     CONSTRAINT PRIMARY KEY (userId)
);

create table employeeDTO(
                         userId VARCHAR(6) NOT NULL,
                         empId VARCHAR(6) NOT NULL,
                         empName VARCHAR(30),
                         gender VARCHAR(10),
                         email VARCHAR(50),
                         nic VARCHAR(15),
                         address VARCHAR(20),
                         CONSTRAINT PRIMARY KEY (empId),
                         CONSTRAINT FOREIGN KEY(userId) REFERENCES userDTO(userId) on Delete Cascade on Update Cascade
);

create table scheduleDTO(
                         scheduleId VARCHAR(6) NOT NULL,
                         scheduleDetails VARCHAR(255),
                         CONSTRAINT PRIMARY KEY (scheduleId)
);

CREATE TABLE scheduleDetail(
                               scheduleId VARCHAR(6) NOT NULL,
                               empId VARCHAR(6) NOT NULL,
                               date DATE,
                               CONSTRAINT PRIMARY KEY (scheduleId,empId),
                               CONSTRAINT FOREIGN KEY (scheduleId) REFERENCES scheduleDTO(scheduleId) on Delete Cascade on Update Cascade,
                               CONSTRAINT FOREIGN KEY (empId) REFERENCES employeeDTO(empId) on Delete Cascade on Update Cascade
);

create table guestDTO(
                      userId VARCHAR(6) NOT NULL,
                      guestId VARCHAR(6) NOT NULL,
                      guestName VARCHAR(30),
                      gender VARCHAR(10),
                      guestCountry VARCHAR(10),
                      guestZipcode VARCHAR(15),
                      guestPassportId VARCHAR(20),
                      CONSTRAINT PRIMARY KEY (guestId),
                      CONSTRAINT FOREIGN KEY(userId) REFERENCES userDTO(userId) on Delete Cascade on Update Cascade
);



create table roomDTO(
                     roomId VARCHAR(6) NOT NULL,
                     roomDetails VARCHAR(255),
                     roomType VARCHAR(200),
                     roomPrice DECIMAL (10,2),
                     CONSTRAINT PRIMARY KEY (roomId)
);

create table bookingDTO(
                        guestId VARCHAR(6) NOT NULL,
                        bookingId VARCHAR(6) NOT NULL,
                        bookingDate DATE,
                        roomId VARCHAR(6) NOT NULL,
                        checkIn DATE,
                        checkOut DATE,
                        CONSTRAINT PRIMARY KEY (bookingId),
                        CONSTRAINT FOREIGN KEY(guestId) REFERENCES guestDTO(guestId) on Delete Cascade on Update Cascade,
                        CONSTRAINT FOREIGN KEY(roomId) REFERENCES roomDTO(roomId) on Delete Cascade on Update Cascade
);

create table tourDTO(
                     tourId VARCHAR(6) NOT NULL,
                     tourName VARCHAR(30),
                     tourDetails VARCHAR(255),
                     price DECIMaL(10,2),
                     CONSTRAINT PRIMARY KEY (tourId)
);

CREATE TABLE tourDetail(
                           bookingId VARCHAR(6) NOT NULL,
                           tourId VARCHAR(6) NOT NULL,
                           amount INT(11),
                           date DATE,
                           CONSTRAINT PRIMARY KEY (bookingId,tourId),
                           CONSTRAINT FOREIGN KEY (bookingId) REFERENCES bookingDTO(bookingId) on Delete Cascade on Update Cascade,
                           CONSTRAINT FOREIGN KEY (tourId) REFERENCES tourDTO(tourId) on Delete Cascade on Update Cascade
);

create table foodDTO(
                     foodId VARCHAR(6) NOT NULL,
                     foodName VARCHAR(100),
                     foodDetails VARCHAR(255),
                     foodPrice DECIMaL(10,2),
                     CONSTRAINT PRIMARY KEY (foodId)
);
CREATE TABLE foodOrders(
                           orderId VARCHAR(10) NOT NULL,
                           date DATE,
                           bookingId VARCHAR(6) NOT NULL,
                           CONSTRAINT PRIMARY KEY (orderId),
                           CONSTRAINT FOREIGN KEY(bookingId) REFERENCES bookingDTO(bookingId) on Delete Cascade on Update Cascade
);

CREATE TABLE foodOrderDetail(
                                orderId VARCHAR(10) NOT NULL,
                                foodId VARCHAR(6) NOT NULL,
                                qty INT(11),
                                amount DECIMAL(8,2),
                                date DATE,
                                CONSTRAINT PRIMARY KEY (orderId),
                                CONSTRAINT FOREIGN KEY (orderId) REFERENCES foodOrders(orderId) on Delete Cascade on Update Cascade,
                                CONSTRAINT FOREIGN KEY (foodId) REFERENCES foodDTO(foodId) on Delete Cascade on Update Cascade
);


create table paymentDTO(
                        paymentId VARCHAR(20) NOT NULL,
                        guestId VARCHAR(30) NOT NULL,
                        guestName VARCHAR(30),
                        bookingId VARCHAR(6) NOT NULL,
                        roomId VARCHAR(6) NOT NULL ,
                        checkInDate DATE NOT NULL,
                        checkOutDate DATE  NOT NULL,
                        ordersAmount DECIMAL(10,2),
                        totalPrice DECIMAL(10,2),
                        CONSTRAINT PRIMARY KEY (paymentId),
                        CONSTRAINT FOREIGN KEY (guestId) REFERENCES guestDTO (guestId) ON Delete Cascade On update cascade,
                        CONSTRAINT FOREIGN KEY (bookingId)  REFERENCES bookingDTO (bookingId) ON Delete Cascade On update cascade,
                        CONSTRAINT FOREIGN KEY (roomId) REFERENCES roomDTO (roomId) ON Delete Cascade On update cascade
);

create table supplierDTO(
                         supId VARCHAR(6) NOT NULL,
                         supName VARCHAR(30),
                         supContact VARCHAR(15),
                         supplyDetail VARCHAR(255),
                         CONSTRAINT PRIMARY KEY (supId)
);

create table inventoryDTO(
                          itemId VARCHAR(6) NOT NULL,
                          itemName VARCHAR(30),
                          itemDetails VARCHAR(255),
                          itemPrice DECIMaL(10,2),
                          CONSTRAINT PRIMARY KEY (itemId)
);

CREATE TABLE supplyDetail(
                             supId VARCHAR(6) NOT NULL,
                             itemId VARCHAR(6) NOT NULL,
                             date DATE,
                             CONSTRAINT PRIMARY KEY (supId,itemId),
                             CONSTRAINT FOREIGN KEY (supId) REFERENCES supplierDTO(supId) on Delete Cascade on Update Cascade,
                             CONSTRAINT FOREIGN KEY (itemId) REFERENCES inventoryDTO(itemId) on Delete Cascade on Update Cascade
);
