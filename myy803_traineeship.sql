USE `myy803_traineeship`;

-- Table: Users
CREATE TABLE IF NOT EXISTS Users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Table: Students
CREATE TABLE IF NOT EXISTS Students (
    student_id INT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    university_id VARCHAR(255) NOT NULL,
    preferred_location VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES Users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: Companies
CREATE TABLE IF NOT EXISTS Companies (
    company_id INT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    FOREIGN KEY (company_id) REFERENCES Users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: Professors
CREATE TABLE IF NOT EXISTS Professors (
    professor_id INT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES Users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: CommitteeMembers
CREATE TABLE IF NOT EXISTS CommitteeMembers (
    committee_member_id INT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (committee_member_id) REFERENCES Users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: Interests
CREATE TABLE IF NOT EXISTS Interests (
    interest_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: Skills
CREATE TABLE IF NOT EXISTS Skills (
    skill_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: StudentInterests
CREATE TABLE IF NOT EXISTS StudentInterests (
    student_id INT,
    interest_id INT,
    PRIMARY KEY (student_id, interest_id),
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES Interests(interest_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: StudentSkills
CREATE TABLE IF NOT EXISTS StudentSkills (
    student_id INT,
    skill_id INT,
    PRIMARY KEY (student_id, skill_id),
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES Skills(skill_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: ProfessorInterests
CREATE TABLE IF NOT EXISTS ProfessorInterests (
    professor_id INT,
    interest_id INT,
    PRIMARY KEY (professor_id, interest_id),
    FOREIGN KEY (professor_id) REFERENCES Professors(professor_id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES Interests(interest_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: TraineeshipPositions
CREATE TABLE IF NOT EXISTS TraineeshipPositions (
    position_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    status ENUM('available', 'assigned', 'closed') DEFAULT 'available',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES Companies(company_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: TraineeshipPositionSkills
CREATE TABLE IF NOT EXISTS TraineeshipPositionSkills (
    position_id INT,
    skill_id INT,
    PRIMARY KEY (position_id, skill_id),
    FOREIGN KEY (position_id) REFERENCES TraineeshipPositions(position_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES Skills(skill_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: TraineeshipPositionTopics
CREATE TABLE IF NOT EXISTS TraineeshipPositionTopics (
    position_id INT,
    interest_id INT,
    PRIMARY KEY (position_id, interest_id),
    FOREIGN KEY (position_id) REFERENCES TraineeshipPositions(position_id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES Interests(interest_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: Applications
CREATE TABLE IF NOT EXISTS Applications (
    application_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    position_id INT NOT NULL,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'accepted', 'rejected') DEFAULT 'pending',
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (position_id) REFERENCES TraineeshipPositions(position_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: TraineeshipAssignments
CREATE TABLE IF NOT EXISTS TraineeshipAssignments (
    assignment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    position_id INT NOT NULL,
    supervisor_id INT DEFAULT NULL,
    assignment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('in_progress', 'completed') DEFAULT 'in_progress',
    final_result ENUM('pass', 'fail') DEFAULT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (position_id) REFERENCES TraineeshipPositions(position_id) ON DELETE CASCADE,
    FOREIGN KEY (supervisor_id) REFERENCES Professors(professor_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: TraineeshipLogbook
CREATE TABLE IF NOT EXISTS TraineeshipLogbook (
    log_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT NOT NULL,
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    content TEXT,
    FOREIGN KEY (assignment_id) REFERENCES TraineeshipAssignments(assignment_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: CompanyEvaluations
CREATE TABLE IF NOT EXISTS CompanyEvaluations (
    evaluation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT NOT NULL,
    student_motivation TINYINT NOT NULL,
    effectiveness TINYINT NOT NULL,
    efficiency TINYINT NOT NULL,
    evaluation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES TraineeshipAssignments(assignment_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: ProfessorEvaluations
CREATE TABLE IF NOT EXISTS ProfessorEvaluations (
    evaluation_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT NOT NULL,
    student_motivation TINYINT NOT NULL,
    effectiveness TINYINT NOT NULL,
    efficiency TINYINT NOT NULL,
    company_facilities TINYINT NOT NULL,
    company_guidance TINYINT NOT NULL,
    evaluation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES TraineeshipAssignments(assignment_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;