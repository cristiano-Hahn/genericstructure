CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    enabled BOOLEAN NOT NULL,
    phone TEXT NOT NULL,
    roles TEXT[] NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT fk_user_company_id FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);


create unique index user_email_unique_idx on "user"(email);