-- ─────────────────────────────────────────
-- 1. DEPARTMENTS (insert first — doctor needs department_id)
-- ─────────────────────────────────────────
INSERT INTO department (name, description, floor_number) VALUES
                                                             ('Cardiology',      'Heart and cardiovascular diseases',         2),
                                                             ('Neurology',       'Brain and nervous system disorders',        3),
                                                             ('Orthopedics',     'Bone, joint and muscle conditions',         4),
                                                             ('General Medicine','Common illnesses, fever, infections',       1),
                                                             ('Dermatology',     'Skin, hair and nail conditions',            2);

-- $2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq
-- $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lHHG
-- ─────────────────────────────────────────
-- 2. USERS
-- All passwords below are BCrypt hash of:  Test@1234
-- ─────────────────────────────────────────
INSERT INTO user_table (name, email, password, role, contact_number, is_active, created_at, updated_at) VALUES

-- Admin
('Admin User',
 'admin@hospital.com',
 '$2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq',
 'ADMIN',
 '9000000001',
 true,
 NOW(), NOW()),

-- Doctors
('Dr. Rajesh Kumar',
 'rajesh@hospital.com',
 '$2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq',
 'DOCTOR',
 '9000000002',
 true,
 NOW(), NOW()),

('Dr. Priya Sharma',
 'priya@hospital.com',
 '$2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq',
 'DOCTOR',
 '9000000003',
 true,
 NOW(), NOW()),

-- Patients
('Amandeep Singh',
 'amandeep@gmail.com',
 '$2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq',
 'PATIENT',
 '9000000004',
 true,
 NOW(), NOW()),

('Simran Kaur',
 'simran@gmail.com',
 '$2a$10$oTxu7dJy1ZKLfZImUH77w.x1g/Q.wTOUxgPSqP/glkmeaRs.50Xoq',
 'PATIENT',
 '9000000005',
 true,
 NOW(), NOW());


-- ─────────────────────────────────────────
-- 3. DOCTORS
-- user_id 2 = Dr. Rajesh, user_id 3 = Dr. Priya
-- department_id 1 = Cardiology, 2 = Neurology
-- ─────────────────────────────────────────
INSERT INTO doctor (user_id, department_id, specialization, qualification,
                    experience_years, consultation_fee,
                    available_from, available_to, bio) VALUES

                                                           (2, 1,
                                                            'Interventional Cardiology',
                                                            'MBBS, MD Cardiology',
                                                            10,
                                                            800.00,
                                                            '09:00:00', '17:00:00',
                                                            'Senior cardiologist with 10 years of experience in heart disease management'),

                                                           (3, 2,
                                                            'Clinical Neurology',
                                                            'MBBS, MD Neurology',
                                                            7,
                                                            700.00,
                                                            '10:00:00', '16:00:00',
                                                            'Specialist in brain and nervous system disorders');


-- ─────────────────────────────────────────
-- 4. DOCTOR AVAILABLE DAYS
-- doctor_id 1 = Dr. Rajesh, doctor_id 2 = Dr. Priya
-- table name is doctor_available_days (Spring creates this for @ElementCollection)
-- ─────────────────────────────────────────
INSERT INTO doctor_available_days (doctor_id, available_days) VALUES
                                                                  (1, 'MONDAY'),
                                                                  (1, 'TUESDAY'),
                                                                  (1, 'WEDNESDAY'),
                                                                  (1, 'FRIDAY'),
                                                                  (2, 'MONDAY'),
                                                                  (2, 'WEDNESDAY'),
                                                                  (2, 'THURSDAY'),
                                                                  (2, 'FRIDAY');


-- ─────────────────────────────────────────
-- 5. PATIENTS
-- user_id 4 = Amandeep, user_id 5 = Simran
-- ─────────────────────────────────────────
INSERT INTO patient (user_id, gender, birth_date, blood_group,
                     address, emergency_contact,
                     medical_history, created_at) VALUES

                                                      (4,
                                                       'MALE',
                                                       '1998-05-15',
                                                       'B_POSITIVE',
                                                       'Ludhiana, Punjab',
                                                       '9111111111',
                                                       'No known allergies',
                                                       NOW()),

                                                      (5,
                                                       'FEMALE',
                                                       '2000-11-22',
                                                       'O_POSITIVE',
                                                       'Mohali, Punjab',
                                                       '9222222222',
                                                       'Mild asthma',
                                                       NOW());