CREATE TABLE health_tip (
  id INT PRIMARY KEY,
  title VARCHAR(255)
);

CREATE TABLE health_tip_detail (
  id INT PRIMARY KEY,
  detailed_description TEXT,
  image_url VARCHAR(255),
  source VARCHAR(255)
);