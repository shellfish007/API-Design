let doctors =[{
        id: 0,
        name: "Alice",
        clinic: "Excel Dental",
        specialty: "Teeth Filling",
        availableSlots: [
            "9:00 - 9:30", "9:30 - 10:00", "10:30 - 11:00",
            "11:00 - 11:30", "11:30 - 12:00", "12:00 - 12:30", "12:30 - 13:00",
            "13:00 - 13:30", "13:30 - 14:00", "14:00 - 14:30", "14:30 - 15:00",
            "15:30 - 16:00", "16:00 - 16:30", "16:30 - 17:00",
        ]
    },{
        id: 1,
        name: "Makky",
        clinic: "UPMC",
        specialty: "Coughing",
        availableSlots: [
            "9:00 - 9:30", "9:30 - 10:00", "10:00 - 10:30", "10:30 - 11:00",
            "11:00 - 11:30", "11:30 - 12:00", "12:00 - 12:30", "12:30 - 13:00",
            "13:30 - 14:00", "14:00 - 14:30", "14:30 - 15:00",
            "15:30 - 16:00", "16:00 - 16:30", "16:30 - 17:00",
        ]
    }]

let appointments = [{
        id: 0,
        doctorID: 0,
        patient: "Bob",
        time: "15:00 - 15:30"
    },{
        id: 1,
        doctorID: 1,
        patient: "Bob",
        time: "15:00 - 15:30"
    },{
        id: 2,
        doctorID: 1,
        patient: "David",
        time: "13:00 - 13:30"
    },{
        id: 3,
        doctorID: 0,
        patient: "Helen",
        time: "10:00 - 10:30"
    }]
  
module.exports = {
    doctors,
    appointments
}
 