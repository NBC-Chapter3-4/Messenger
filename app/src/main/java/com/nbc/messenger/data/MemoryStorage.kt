package com.nbc.messenger.data

import com.nbc.messenger.R
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User

object MemoryStorage {

    val users = listOf(
        User(
            id = "1",
            name = "Alice",
            nickname = "ali",
            phoneNumber = "01039263191",
            email = "alice@example.com",
            group = listOf("Group A"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_profile_default),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "2",
            name = "Bob",
            nickname = "bob",
            phoneNumber = "01022905528",
            email = "bob@example.com",
            group = listOf("Group A", "Group B"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample1),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "3",
            name = "Charlie",
            nickname = "charlie",
            phoneNumber = "01081669528",
            email = "charlie@example.com",
            group = listOf("Group C"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample2),
            isLike = true,
            isChecked = true
        ),
        User(
            id = "4",
            name = "David",
            nickname = "dave",
            phoneNumber = "01043691614",
            email = "david@example.com",
            group = listOf("Group D", "Group C"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample3),
            isLike = false,
            isChecked = false
        ),
        User(
            id = "5",
            name = "Emma",
            nickname = "emma",
            phoneNumber = "01059615654",
            email = "emma@example.com",
            group = listOf("Group E"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample4),
            isLike = true,
            isChecked = true
        ),
        User(
            id = "6",
            name = "Frank",
            nickname = "frank",
            phoneNumber = "01079037424",
            email = "frank@example.com",
            group = listOf("Group F"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample5),
            isLike = false,
            isChecked = false
        ),
        User(
            id = "7",
            name = "Grace",
            nickname = "grace",
            phoneNumber = "01057977006",
            email = "grace@example.com",
            group = listOf("Group G"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample6),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "8",
            name = "Henry",
            nickname = "hank",
            phoneNumber = "01022897926",
            email = "henry@example.com",
            group = listOf("Group H"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample7),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "9",
            name = "Ivy",
            nickname = "ivy",
            phoneNumber = "01069451133",
            email = "ivy@example.com",
            group = listOf("Group I"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample8),
            isLike = true,
            isChecked = true
        ),
        User(
            id = "10",
            name = "Jack",
            nickname = "jack",
            phoneNumber = "01021913855",
            email = "jack@example.com",
            group = listOf("Group J"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample9),
            isLike = false,
            isChecked = false
        ),
        User(
            id = "11",
            name = "Kate",
            nickname = "kate",
            phoneNumber = "01040467466",
            email = "kate@example.com",
            group = listOf("Group K"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample10),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "12",
            name = "Leo",
            nickname = "leo",
            phoneNumber = "01083169162",
            email = "leo@example.com",
            group = listOf("Group L"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample11),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "13",
            name = "Mia",
            nickname = "mia",
            phoneNumber = "01050262468",
            email = "mia@example.com",
            group = listOf("Group M"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample12),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "14",
            name = "Noah",
            nickname = "noah",
            phoneNumber = "01071225285",
            email = "noah@example.com",
            group = listOf("Group N"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample13),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "15",
            name = "Olivia",
            nickname = "olivia",
            phoneNumber = "01065393472",
            email = "olivia@example.com",
            group = listOf("Group O"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample4),
            isLike = true,
            isChecked = true
        ),
        User(
            id = "16",
            name = "Peter",
            nickname = "peter",
            phoneNumber = "01053621534",
            email = "peter@example.com",
            group = listOf("Group P"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample15),
            isLike = false,
            isChecked = false
        ),
        User(
            id = "17",
            name = "Quinn",
            nickname = "quinn",
            phoneNumber = "01039223481",
            email = "quinn@example.com",
            group = listOf("Group Q"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample16),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "18",
            name = "Ryan",
            nickname = "ryan",
            phoneNumber = "01054229907",
            email = "ryan@example.com",
            group = listOf("Group R"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample18),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "19",
            name = "Sophia",
            nickname = "sophia",
            phoneNumber = "01018095777",
            email = "sophia@example.com",
            group = listOf("Group S"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample12),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "20",
            name = "Thomas",
            nickname = "thomas",
            phoneNumber = "01054134822",
            email = "thomas@example.com",
            group = listOf("Group T"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample4),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "21",
            name = "Ursula",
            nickname = "ursula",
            phoneNumber = "01031969716",
            email = "ursula@example.com",
            group = listOf("Group U"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample8),
            isLike = true,
            isChecked = true
        ),
        User(
            id = "22",
            name = "Victor",
            nickname = "victor",
            phoneNumber = "01027992167",
            email = "victor@example.com",
            group = listOf("Group V"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_profile_default),
            isLike = false,
            isChecked = false
        ),
        User(
            id = "23",
            name = "Wendy",
            nickname = "wendy",
            phoneNumber = "01028798840",
            email = "wendy@example.com",
            group = listOf("Group W"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample15),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "24",
            name = "Xander",
            nickname = "xander",
            phoneNumber = "01033073793",
            email = "xander@example.com",
            group = listOf("Group X"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample17),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "25",
            name = "Yvonne",
            nickname = "yvonne",
            phoneNumber = "01091279456",
            email = "yvonne@example.com",
            group = listOf("Group Y"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample9),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "26",
            name = "Zane",
            nickname = "zane",
            phoneNumber = "01094272541",
            email = "zane@example.com",
            group = listOf("Group Z"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_profile_default),
            isLike = false,
            isChecked = true
        ),
        User(
            id = "27",
            name = "Anna",
            nickname = "anna",
            phoneNumber = "01093963289",
            email = "anna@example.com",
            group = listOf("Group A"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample3),
            isLike = true,
            isChecked = true
        ),
        User(
            id = "28",
            name = "Ben",
            nickname = "ben",
            phoneNumber = "01034249434",
            email = "ben@example.com",
            group = listOf("Group B"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_profile_default),
            isLike = false,
            isChecked = false
        ),
        User(
            id = "29",
            name = "Cathy",
            nickname = "cathy",
            phoneNumber = "01039223797",
            email = "cathy@example.com",
            group = listOf("Group C"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_sample5),
            isLike = true,
            isChecked = false
        ),
        User(
            id = "30",
            name = "David",
            nickname = "david",
            phoneNumber = "01039474313",
            email = "david@example.com",
            group = listOf("Group D"),
            profileImage = ProfileImage.ResourceImage(R.drawable.ic_profile_default),
            isLike = false,
            isChecked = true
        ),
    )
}