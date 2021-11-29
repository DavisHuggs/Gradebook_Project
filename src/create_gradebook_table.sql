USE mydatabase;

CREATE TABLE IF NOT EXISTS Gradebook
(
  assignmenttype	VARCHAR(20),
  score	INT,
  letter	VARCHAR(1),
  name	VARCHAR(100),
  date VARCHAR(10),
  uniquevariable	VARCHAR(100)
);