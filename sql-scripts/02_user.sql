use kino;

INSERT INTO `account` (`id`, `birthday`, `email`, `firstName`, `hashedPassword`, `lastName`, `role`, `salt`)
VALUES ('1', '2019-05-09', 'admin@account.de', 'Admin',
        '64f73e3451c5def78775e060f68e2c94b4d53b1d2c5375917042cf8b08ca0dc77b7459e557b6ed7ef83fd4cb4f30697968aaa3008e70505507707eecbb468b5e',
        'Account', '0', 0x4d533cd6317305b3ecb4cd06afe26376),
       ('2', '2019-05-09', 'moderator@account.de', 'Moderator',
        '8e89d4f48e9a81f7a6d6a687c3b93f1cf9e58e323f9a0ba82beab4766245cc37c800ab16136f62f722e98859903643ae5c74189c961ba73d456d13da482efbb9',
        'Account', '1', 0x4dbd312e02b619ac8a57cd596352bdef),
       ('3', '2019-05-09', 'customer@account.de', 'Emilia',
        '7cc0e2f8aeefc0f2c07e86ebbf40beab7dca4128a8f9b46051608974cd522c50e78dfcb321b9204a30a0a88ae7deaec53455d6a8d7850db1755529027a3f26b9',
        'Clarke', '2', 0x91f1cdcc46daacf42cc5639d0c56d026),
       ('4', '2019-05-09', 'customer1@account.de', 'Alicia',
        'c61dffc876d3b92499b7fcbf19e183bb85eb0a5ed980674defd10d4670cd15b77242b69011bd71c1167d85741dfd9230bfd555bfbec7d0a4875bd3571865f6c4',
        'Vikander', '2', 0x2a870ff77542121091862f8801497bd8);
