let {doctors, appointments} = require("./db");
const {GraphQLError} = require("graphql/error");
index = 0;

const ErrorMsg = (message) => {
    throw new GraphQLError(
        message,
        { extensions: { http: { status: 400 }}});
}

const Query = {
    doctorById:(_, {doctorID}) => {
        let doctor = doctors.find(doc => doc.id === doctorID);
        if (doctor === undefined)
            ErrorMsg('Doctor ID not exist');
        return doctor;
    },

    appointmentById:(_, {appointmentID}) => {
        let appointment = appointments.find(ap => ap.id === appointmentID);
        if (appointment === undefined)
            ErrorMsg('Appointment not exist');
        return appointment;
    },

    availableSlots:(_, {doctorID}) => {
        let doctor = doctors.find(d => d.id === doctorID);
        if (doctor === undefined)
            ErrorMsg('Doctor ID not exist');
        return doctor.availableSlots;
    }
}

const Mutation = {
    bookAppointment:(_, {doctorID, patient, time}) => {
        let appointment = {};
        appointment.id = appointments.length;
        appointment.doctorID = doctorID;
        appointment.time = time;
        appointment.patient = patient;
        let doctorIndex = doctors.findIndex(doc => doc.id === doctorID);
        if (doctorIndex === -1)
            ErrorMsg('Doctor ID not exist');
        if (doctors[doctorIndex].availableSlots.find(slot => slot === time) === undefined)
            ErrorMsg('Unavailable time slot');
        doctors[doctorIndex].availableSlots =
            doctors[doctorIndex].availableSlots.filter(slot => slot !== time);
        return appointment;
    },

    cancelAppointment:(_, {appointmentID}) => {
        let appointment = appointments.find(ap => ap.id === appointmentID);
        if (appointment === undefined)
            ErrorMsg('Appointment not exist');
        appointments = appointments.filter(ap => ap.id !== appointmentID);
        let doctorIndex = doctors.findIndex(doc => doc.id === appointment.doctorID);
        doctors[doctorIndex].availableSlots =
            doctors[doctorIndex].availableSlots.filter(slot => slot !== appointment.time);
    },

    updateAppointment:(_, {appointmentID, patient}) => {
        let appointmentIndex = appointments.findIndex(ap => ap.id === appointmentID);
        if (appointmentIndex === -1)
            ErrorMsg('Appointment not exist');
        if (appointments[appointmentIndex].patient === patient)
            ErrorMsg('Patient not updated');
        appointments[appointmentIndex].patient = patient;
        return appointments[appointmentIndex];
    }
}
 
module.exports = {
    Query,
    Mutation
}
