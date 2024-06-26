import { useEffect, useState } from "react";
import { getShopInfoType } from "../../types/shop/getShopInfoType";
import getShopInfoAPI from "../../service/shop/getShopInfoAPI";
import ShopInfo from "../../components/shop/ShopInfo";
import MySalesList from "../../components/shop/MySalesList";
import { SalesInfoType } from "../../types/shop/SalesInfoType";
import GetMySalesListAPI from "../../service/shop/GetMySalesListAPI";
import ReviewList from "../../components/review/ReviewList";

const ShopInfoPage = () => {
  // 가게 정보
  const [shopInfo, setShopInfo] = useState<getShopInfoType>({
    registeredName: "",
    registeredNo: 0,
    bossName: "",
    address: "",
    roadAddress: "",
    latitude: 0,
    longitude: 0,
    tel: "",
    name: "",
    imageURL: "",
    operatingTime: "",
    holiday: "",
    originCountry: "",
    introduction: "",
    categoryList: [
      {
        categoryId: 0,
        name: "",
        imageURL: "",
      },
    ],
  });
  // 판매 내역 정보
  const [salesLists, setSalesLists] = useState<SalesInfoType[]>([]);
  const [listTap, setListTap] = useState<number>(1);
  const { getSalesSeller } = GetMySalesListAPI();
  const { getShopInfo } = getShopInfoAPI();

  useEffect(() => {
    getShopInfo(setShopInfo);
    getSalesSeller(setSalesLists);
  }, []);

  return (
    <div className="flex justify-center pt-24 pb-24">
      <div className=" bg-white w-[800px] border-gray-300 border-4 rounded-[40px] p-10  overflow-y-scroll ">
        <ShopInfo
          imageURL={shopInfo.imageURL}
          name={shopInfo.name}
          roadAddress={shopInfo.roadAddress}
          introduction={shopInfo.introduction}
          tel={shopInfo.tel}
        />
        <div className="w-full flex flex-row my-5">
          <div onClick={() => setListTap(1)} className="w-full">
            <h1
              className={`rounded-[20px] border-b-2 w-full flex flex-row justify-center items-center h-[60px] ${listTap === 1 ? "bg-orange-300 border-2" : ""}`}
            >
              리뷰
            </h1>
          </div>
          <div onClick={() => setListTap(2)} className="w-full">
            <h1
              className={`rounded-[20px] border-b-2 w-full flex flex-row justify-center items-center h-[60px] ${listTap === 2 ? "bg-orange-300 border-2" : ""}`}
            >
              등록된 물품 목록
            </h1>
          </div>
        </div>
        {listTap === 1 && <ReviewList />}
        {listTap === 2 && <MySalesList salesLists={salesLists} />}
      </div>
    </div>
  );
};

export default ShopInfoPage;
