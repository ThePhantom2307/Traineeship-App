USE `myy803_traineeship`;

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'COMPANY', 'PROFESSOR', "TRAINEESHIP_COMMITTEE_MEMBER")
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Table: students
CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    university_id VARCHAR(255) NOT NULL,
    preferred_location VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: companies
CREATE TABLE IF NOT EXISTS companies (
    company_id INT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    FOREIGN KEY (company_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: professors
CREATE TABLE IF NOT EXISTS professors (
    professor_id INT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: committee_members
CREATE TABLE IF NOT EXISTS committee_members (
    committee_member_id INT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    FOREIGN KEY (committee_member_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: interests
CREATE TABLE IF NOT EXISTS interests (
    interest_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: skills
CREATE TABLE IF NOT EXISTS skills (
    skill_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: student_interests
CREATE TABLE IF NOT EXISTS student_interests (
    student_id INT,
    interest_id INT,
    PRIMARY KEY (student_id, interest_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES interests(interest_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: student_skills
CREATE TABLE IF NOT EXISTS student_skills (
    student_id INT,
    skill_id INT,
    PRIMARY KEY (student_id, skill_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: professor_interests
CREATE TABLE IF NOT EXISTS professor_interests (
    professor_id INT,
    interest_id INT,
    PRIMARY KEY (professor_id, interest_id),
    FOREIGN KEY (professor_id) REFERENCES professors(professor_id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES interests(interest_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: applications
CREATE TABLE IF NOT EXISTS applications (
    application_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING',
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
