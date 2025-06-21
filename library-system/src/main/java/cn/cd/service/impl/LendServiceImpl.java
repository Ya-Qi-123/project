    @Override
    public TLendrecord countByCategory() {
        TLendrecord lendrecord = lendrecordMapper.countByCategory();
        // Ensure that only one result is returned
        return lendrecord;
    }