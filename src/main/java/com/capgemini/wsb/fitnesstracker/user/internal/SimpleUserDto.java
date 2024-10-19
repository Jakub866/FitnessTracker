package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

record SimpleUserDto(@Nullable Long Id, String firstName, String lastName) {

}

record EmailSimpleUserDto(@Nullable Long id, String email) {

}
